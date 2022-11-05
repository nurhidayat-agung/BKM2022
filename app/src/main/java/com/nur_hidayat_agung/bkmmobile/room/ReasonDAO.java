package com.nur_hidayat_agung.bkmmobile.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nur_hidayat_agung.bkmmobile.model.workshop.DataItemWorkshopGetReason;

import java.util.List;

@Dao
public interface ReasonDAO {
    @Query("SELECT * FROM DataItemWorkshopGetReason")
    List<DataItemWorkshopGetReason> getAll();

    @Query("SELECT * FROM DataItemWorkshopGetReason WHERE id IN (:ids)")
    List<DataItemWorkshopGetReason> loadAllByIds(int[] ids);

    @Insert
    void insertAll(DataItemWorkshopGetReason... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void persists(DataItemWorkshopGetReason data);

    @Delete
    void delete(DataItemWorkshopGetReason data);

    @Query("DELETE FROM DataItemWorkshopGetReason")
    void deleteAll();
}
