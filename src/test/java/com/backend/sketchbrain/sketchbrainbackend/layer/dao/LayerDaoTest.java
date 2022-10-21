package com.backend.sketchbrain.sketchbrainbackend.layer.dao;

import com.backend.sketchbrain.sketchbrainbackend.layer.dto.Layer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LayerDaoTest {

    private final LayerDao layerDao;

    @Autowired
    public LayerDaoTest(LayerDao layerDao){
        this.layerDao = layerDao;
    }

    @Test
    @DisplayName("DB 에서 전체 Layer 리스트에 대한 조회가 정상 작동한다.")
    void layerList(){
        assertFalse(layerDao.layerList().isEmpty());
    }

    @Test
    @DisplayName("DB 에서 Layer 이름을 통한 Layer 에 대한 조회가 정상 작동한다.")
    void layerParameter() {
        List<Layer> layerList = layerDao.layerList();
        if(layerList.isEmpty())
            fail();
        String layerName = layerList.get(0).getLayer_name();
        assertFalse(layerDao.layerParameter(layerName).isEmpty());
    }

    @Test
    @DisplayName("Layer 이름이 DB에 없으면, 빈 값이 반환된다.")
    void layerParameterIncorrectLayerName(){
        List<Layer> layerList = layerDao.layerList();
        if(layerList.isEmpty())
            fail();
        String layerName = "@incorrect! layer^ &name*";
        assertTrue(layerDao.layerParameter(layerName).isEmpty());
    }
}