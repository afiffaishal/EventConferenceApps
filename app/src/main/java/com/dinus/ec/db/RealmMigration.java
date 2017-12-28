package com.dinus.ec.db;

/**
 * Created by crocodic-mbp-9 on 11/29/17.
 */

import io.realm.DynamicRealm;
import io.realm.RealmSchema;

/**
 * Created by Ridwan Akbar on 10/18/17.
 */

public class RealmMigration implements io.realm.RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

//        if (oldVersion == 0) {
//            final RealmObjectSchema userSchema = schema.get("PlafondApproval");
//            userSchema.addField("type", String.class);
//        }
//        else if (oldVersion == 1){
//            final RealmObjectSchema userSchema = schema.get("Promo");
//            userSchema.addField("date", String.class);
//        }else if (oldVersion == 2){
//            final RealmObjectSchema userSchema = schema.get("User");
//            userSchema.addField("ktpNumber", String.class);
//        }
    }

}
