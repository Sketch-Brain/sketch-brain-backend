package com.backend.sketchbrain.sketchbrainbackend.layer.service;

import com.backend.sketchbrain.sketchbrainbackend.layer.dao.LayerDao;
import com.backend.sketchbrain.sketchbrainbackend.layer.dto.Layer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class LayerServiceTest {

    private List<Layer> testLayerList;
    private LayerService layerService;

    @Mock
    private LayerDao layerDao;

    @Autowired
    public LayerServiceTest(LayerService layerService){
        this.layerService = layerService;
    }

    @BeforeEach
    void init(){
        List<Layer> layerListReturn = List.of(new Layer[]{
                                new Layer(1,"activation","activation_param"),
                                new Layer(2,"conv2d","conv2d_parameter"),
                                new Layer(3,"flatten","flatten_parameter")
                        });
        this.testLayerList = layerListReturn;
        this.layerService = new LayerService(this.layerDao);
    }

    @Test
    void getLayerList() {
        given(layerDao.layerList()).willReturn(testLayerList);
        assertEquals(layerService.getLayerList().size(),3);
    }
    @Test
    void getLayerParameter() {
        List<Layer> layerListReturnTmp = new ArrayList<>();
        String layerName = "activation";
        String layerParameter = "activation_param";
        layerListReturnTmp.add(testLayerList.get(0));
        given(layerDao.layerParameter(layerName)).willReturn(layerListReturnTmp);
        assertEquals(layerService.getLayerParameter(layerName),layerParameter);
    }

    @Test
    void getLayerParameterIncorrectLayerName(){
        String layerName = "@incorrect! layer^ &name*";
        given(layerDao.layerParameter(layerName)).willReturn(new ArrayList<>(0));
        assertNull(layerService.getLayerParameter(layerName));
    }
}