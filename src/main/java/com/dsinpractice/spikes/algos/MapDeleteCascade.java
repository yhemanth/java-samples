package com.dsinpractice.spikes.algos;

import java.util.*;

public class MapDeleteCascade {

    private Map<String, String> employeeManagerMap;

    public MapDeleteCascade() {
        employeeManagerMap = new HashMap<String, String>();
    }

    public static void main(String[] args) {
        MapDeleteCascade mapDeleteCascade = new MapDeleteCascade();
        mapDeleteCascade.loadData();
        mapDeleteCascade.deleteEmployeesReportingTo("E5");
        mapDeleteCascade.print("After delete cascade:");
    }

    private void deleteEmployeesReportingTo(String manager) {
        Deque<String> bfsQueue = new ArrayDeque<String>();
        List<String> employeesToRemove = new ArrayList<String>();
        bfsQueue.addAll(employeesReportingTo(manager));
        employeesToRemove.addAll(bfsQueue);
        while (bfsQueue.size()>0) {
            Set<String> reportees = employeesReportingTo(bfsQueue.removeFirst());
            bfsQueue.addAll(reportees);
            employeesToRemove.addAll(reportees);
        }
        System.out.println(employeesToRemove);
    }

    private Set<String> employeesReportingTo(String manager) {
        Map<String, String> tempEmpMgrMap = new HashMap<String, String>(employeeManagerMap);
        tempEmpMgrMap.values().retainAll(Collections.singleton(manager));
        return tempEmpMgrMap.keySet();
    }

    private void print(String s) {
        System.out.println(s + employeeManagerMap);
    }

    private void loadData() {
        String[] employees = new String[]{"E1", "E4", "E5", "E6", "E7", "E8", "E9", "E10", "E11", "E12", "E13"};
        String[] managers = new String[]{null, "E1", "E1", "E5", "E5", "E6", "E6", "E7", "E9", "E9", "E10"};
        for (int i = 0; i < employees.length; i++) {
            employeeManagerMap.put(employees[i], managers[i]);
        }
        print("Initial setup: ");
    }
}
