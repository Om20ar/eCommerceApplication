package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObject(Object target, String fieldName, Object toInject) {
        boolean wasPrivte = false;
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
           // boolean changedToAccessible = false;
            if (!field.isAccessible()) {
                field.setAccessible(true);
                wasPrivte = true;
            }
            field.set(target,toInject);
            if (wasPrivte){
                field.setAccessible(false);
            }

        } catch (NoSuchFieldException e) {
           e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
    }
}
