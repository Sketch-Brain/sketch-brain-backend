package com.backend.sketchbrain.sketchbrainbackend.layer.dao;

import com.backend.sketchbrain.sketchbrainbackend.layer.dto.Layer;
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
    void layerList(){
        assertFalse(layerDao.layerList().isEmpty());
    }

    @Test
    void layerParameter() {
        List<Layer> layerList = layerDao.layerList();
        if(layerList.isEmpty())
            fail();
        String layerName = layerList.get(0).getLayer_name();
        assertFalse(layerDao.layerParameter(layerName).isEmpty());
    }

    @Test
    void layerParameterIncorrectLayerName(){
        List<Layer> layerList = layerDao.layerList();
        if(layerList.isEmpty())
            fail();
        String layerName = "@incorrect! layer^ &name*";
        assertTrue(layerDao.layerParameter(layerName).isEmpty());
    }
}