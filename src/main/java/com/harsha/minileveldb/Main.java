package com.harsha.minileveldb;

public class Main {
    public static void main(String[] args) throws Exception {
        LevelDBImpl db = new LevelDBImpl("data");
        db.put("a", "1");
        System.out.println("get a -> " + db.get("a")); // expect 1
        db.delete("a");
        System.out.println("get a after delete -> " + db.get("a")); // expect null
    }
}
