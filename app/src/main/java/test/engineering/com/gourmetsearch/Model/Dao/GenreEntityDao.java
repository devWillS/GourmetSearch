package test.engineering.com.gourmetsearch.Model.Dao;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import test.engineering.com.gourmetsearch.Entities.GenreEntity;
import test.engineering.com.gourmetsearch.Model.Response.GenreResponse;

public class GenreEntityDao {
    private static GenreEntityDao dao;
    private static Realm realm;

    private GenreEntityDao() {
    }

    public static GenreEntityDao getInstance() {
        if (dao == null) {
            dao = new GenreEntityDao();
        }
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        return dao;
    }

    public boolean add(List<GenreResponse> genreList, boolean delete) {
        boolean bl = false;
        try {
            realm.beginTransaction();
            if (delete) {
                realm.where(GenreEntity.class).findAll().deleteAllFromRealm();
            }
            List<GenreEntity> genreEntityList = new ArrayList();
            for (GenreResponse genre : genreList) {
                GenreEntity genreEntity = realm.createObject(GenreEntity.class, genre.getCode());
                genreEntity.setName(genre.getName());
                genreEntityList.add(genreEntity);
            }
            realm.insert(genreEntityList);
            realm.commitTransaction();
            bl = true;
        } catch (Exception e) {
            Log.e("GenreDao", e.getMessage());
            realm.cancelTransaction();
        }
        return bl;
    }

    public GenreEntity findById(String code) {
        return realm.copyFromRealm(realm.where(GenreEntity.class).equalTo("code", code).findFirst());
    }

    public List<GenreEntity> findAll() {
        RealmResults<GenreEntity> list = realm.where(GenreEntity.class).findAll();
        if (null != list) {
            return realm.copyFromRealm(list);
        }
        return new ArrayList<>();
    }

    /**
     * ジャンル一覧データを削除する
     *
     * @throws SQLException
     */
    public void deleteAll() {
        realm.beginTransaction();
        realm.where(GenreEntity.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    /**
     * ジャンルデータを削除する
     *
     * @throws SQLException
     */
    public boolean delete(String code) {
        boolean isDelete = false;
        try {
            realm.beginTransaction();
            isDelete = realm.where(GenreEntity.class).equalTo("code", code).findAll().deleteAllFromRealm();
            realm.commitTransaction();
        } catch (Exception e) {
            realm.cancelTransaction();
        }
        return isDelete;
    }
}