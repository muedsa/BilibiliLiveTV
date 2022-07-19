package com.muedsa.bilibililivetv.room.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.muedsa.bilibililivetv.room.model.BaseModel;

import io.reactivex.rxjava3.core.Completable;

public interface IBaseDao<M extends BaseModel> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(M model);

    @Update
    void update(M model);

    @Delete
    void deleteById(M model);

    abstract class Wrapper<D extends IBaseDao<M>, M extends BaseModel> implements IBaseDao<M> {
        protected final D dao;

        public Wrapper(D dao) {
            this.dao = dao;
        }

        public void insert(M model) {
            model.setUpdateAt(System.currentTimeMillis());
            dao.insert(model);
        }

        public void update(M model) {
            model.setUpdateAt(System.currentTimeMillis());
            dao.update(model);
        }

        @Override
        public void deleteById(M model) {
            dao.deleteById(model);
        }
    }
}
