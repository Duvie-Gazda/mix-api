package com.mix.api.controller.service;

import com.mix.api.model.Data;
import com.mix.api.model.DataStatus;
import com.mix.api.model.DataType;
import com.mix.api.repository.DataRepository;
import com.mix.api.repository.DataStatusRepository;
import com.mix.api.repository.DataTypeRepository;
import com.mix.api.repository.UserGroupDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;


/*

                   ----- DATA -----

   # create DATA by name
   # create DATA
   # delete DATA
   # update DATA
   # get DATA by id
   # get DATA by name

                  ----- DATA STATUS -----

    # create DATA_STATUS by name
    # create DATA_STATUS
    # update DATA_STATUS
    # delete DATA_STATUS
    # get DATA_STATUS by id
    # get DATA_STATUS by name

                ------ DATA TYPE -----

    # create DATA_TYPE
    # update DATA_TYPE
    # delete DATA_TYPE
    # get DATA_TYPE by id
    # get DATA_TYPE by name


 */

@Component
public class DataService {
    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private DataRepository  dataRepository;

    @Autowired
    private DataStatusRepository dataStatusRepository;


//    DATA

    public void createDataName(String name){
        dataRepository.save(new Data(name));
    }

    public void updateData(Data data){
        dataRepository.save(data);
    }

    public void deleteData(Data data){
        dataRepository.delete(data);
    }

    public void createData(Data data){
        dataRepository.save(data);
    }

    public Data getDataById(Long id){
        return dataRepository.findDataById(id);
    }

    public Data getDataByName(String name){
        return dataRepository.findDataByName(name);
    }

//   DATA STATUS

    public void createDataStatusByName (String name){
        dataStatusRepository.save(
                new DataStatus(name)
        );
    }

    public void createDataStatus(DataStatus dataStatus){
        dataStatusRepository.save(dataStatus);
    }

    public void updateDataStatus(DataStatus dataStatus){
        dataStatusRepository.save(dataStatus);
    }

    public void deleteDataStatus(DataStatus dataStatus){
        dataStatusRepository.delete(dataStatus);
    }

    public DataStatus getDataStatusById(Long id){
        return dataStatusRepository.findDataStatusById(id);
    }

    public DataStatus getDataStatusByName(String name){
        return dataStatusRepository.findDataStatusByName(name);
    }

//    DATA TYPE

    public void createDataTypeByName(String name){
        dataTypeRepository.save(new DataType(name));
    }

    public void createDataType(DataType dataType){
        dataTypeRepository.save(dataType);
    }

    public void updateDataType(DataType dataType){
        dataTypeRepository.save(dataType);
    }

    public void deleteDataType(DataType dataType){
        dataTypeRepository.delete(dataType);
    }

    public DataType getDataTypeById(Long id){
        return dataTypeRepository.findDataTypeById(id);
    }
}
