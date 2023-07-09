package com.dsinpractice.spikes.simplejava;

public class ObjectIdentities {

    public static void main(String[] args) {
        ObjectIdentities obj = new ObjectIdentities();
        int i = System.identityHashCode(obj);
        System.out.println("Identity hashcode of obj: " + i);
        obj.callByReference(obj);
        System.out.println("Identity hashcode of obj after call: " + i);
    }

    private void callByReference(ObjectIdentities obj) {
        System.out.println("Identity hashcode of obj in called method before reassignment: "
                + System.identityHashCode(obj));
        obj = new ObjectIdentities();
        System.out.println("Identity hashcode of obj in called method after reassignment: "
                + System.identityHashCode(obj));
    }
}
