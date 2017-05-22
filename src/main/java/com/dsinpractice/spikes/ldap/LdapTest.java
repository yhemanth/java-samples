package com.dsinpractice.spikes.ldap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;

public class LdapTest {

    private static final int MAX_RETRIES = 1;
    private static final String KNOX_FILE = "/tmp/knoxsso.xml";
    private static final String KNOX_FILE_NEW = "/tmp/knoxsso.xml.new";

    class LdapProperties {

        final String url;
        final String userDNSearchTemplate;
        private final String bindDn;
        private final String passwd;

        LdapProperties(String ldapUrl, String ldapDNSearchTemplate, String bindDn, String passwd) {
            this.url = ldapUrl;
            this.userDNSearchTemplate = ldapDNSearchTemplate;
            this.bindDn = bindDn;
            this.passwd = passwd;
        }

        @Override
        public String toString() {
            return "LdapProperties{" +
                    "url='" + url + '\'' +
                    ", userDNSearchTemplate='" + userDNSearchTemplate + '\'' +
                    ", bindDn='" + bindDn + '\'' +
                    ", passwd='" + passwd + '\'' +
                    '}';
        }

        boolean validate() {
            try {
                Hashtable<String, String> env = new Hashtable<String, String>();
                env.put("com.sun.jndi.ldap.read.timeout", "1000");
                env.put("com.sun.jndi.ldap.connect.timeout", "5000");
                env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                env.put(Context.PROVIDER_URL, url);
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
                env.put(Context.SECURITY_PRINCIPAL, bindDn);
                env.put(Context.SECURITY_CREDENTIALS, passwd);
                DirContext ctx = new InitialDirContext(env);
                ctx.close();
                return true;
            } catch (NamingException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        int numRetries = 0;
        LdapTest ldapTest = new LdapTest();
        LdapProperties ldapProperties = ldapTest.readConfiguration();
        while(!ldapProperties.validate() && numRetries < MAX_RETRIES) {
            System.out.println("Could not connect to LDAP URL " + ldapProperties.url + " with provided credentials. Enter the details again...");
            numRetries++;
            ldapProperties = ldapTest.readConfiguration();
        }
        if (numRetries < MAX_RETRIES) {
            System.out.println("Validated connecting to LDAP URL " + ldapProperties.url + " with credentials");
            configureKnox(ldapProperties);
        } else {
            System.out.println("LDAP connection properties seem invalid. Please start again after checking them.");
        }
    }

    private static void configureKnox(LdapProperties ldapProperties) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document knoxSsoConfig = documentBuilder.parse(new File(KNOX_FILE));
        HashMap<String, Node> paramNamesToNodesMap = buildParamNodesMap(knoxSsoConfig);
        replaceParamValue(paramNamesToNodesMap, "main.ldapRealm.userDnTemplate", ldapProperties.userDNSearchTemplate);
        replaceParamValue(paramNamesToNodesMap, "main.ldapRealm.contextFactory.url", ldapProperties.url);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(knoxSsoConfig);
        StreamResult result = new StreamResult(new File(KNOX_FILE_NEW));
        transformer.transform(source, result);
    }

    private static void replaceParamValue(HashMap<String, Node> paramNamesToNodesMap,
                                          String paramName, String paramValue) {
        Node node = paramNamesToNodesMap.get(paramName);
        System.out.println("Before: " + node.getTextContent());
        NodeList paramNodeChildren = node.getChildNodes();
        for (int i = 0; i < paramNodeChildren.getLength(); i++) {
            Node item = paramNodeChildren.item(i);
            if (item.getNodeName().equals("value")) {
                item.setTextContent(paramValue);
            }
        }
        System.out.println("After: " + node.getTextContent());
    }

    private static HashMap<String, Node> buildParamNodesMap(Document knoxSsoConfig) {
        NodeList paramNodes = knoxSsoConfig.getElementsByTagName("param");
        HashMap<String, Node> paramNameToNodesMap = new HashMap<String, Node>();
        for (int i = 0; i < paramNodes.getLength(); i++) {
            Node paramNode = paramNodes.item(i);
            NodeList paramProperties = paramNode.getChildNodes();
            for (int j = 0; j < paramProperties.getLength(); j++) {
                Node paramProperty = paramProperties.item(j);
                if (paramProperty.getNodeName().equals("name")) {
                    paramNameToNodesMap.put(paramProperty.getFirstChild().getNodeValue(), paramNode);
                }
            }
        }
        return paramNameToNodesMap;
    }

    private LdapProperties readConfiguration() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String ldapUrl = readUserConfiguration("Enter LDAP Server Address", reader);
        String searchBase = readUserConfiguration("Enter search base for identifying users", reader);
        String userAttribute = readUserConfiguration("Enter attribute name used for user IDs in LDAP", reader);
        String adminUserName = readUserConfiguration("Enter the Administrator username", reader);
        Console console = System.console();
        String adminPassword = readPassword("Enter the Administrator password", console);
        String ldapDNSearchTemplate = String.format("%s={0},%s", userAttribute, searchBase);
        LdapProperties ldapProperties = new LdapProperties(ldapUrl, ldapDNSearchTemplate, adminUserName, adminPassword);
        System.out.println("LDAP Properties: " + ldapProperties);
        return ldapProperties;
    }

    private String readPassword(String label, Console console) {
        return new String(console.readPassword(label + ": "));
    }

    private String readUserConfiguration(String label, BufferedReader reader) throws IOException {
        System.out.print(label + ": ");
        return reader.readLine().trim();
    }
}
