package com.fruitdiscovery.service

import com.fruitdiscovery.domain.ProductInfo
import spock.lang.Specification
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Created by sanjidagafur on 10/04/2016.
 */
class ProductInfoDiscoveryServiceSpec extends Specification {

    private ProductInfoDiscoveryService testObj;

    private Server server;

    /**
     * setup before test
     */
    def setup(){
        //initializing test object
        testObj = new ProductInfoDiscoveryService()

        //starting the server before test
        startServer()
    }

    /**
     * cleanup after test
     */
    def cleanup(){
        server.stop()
    }

    def 'getProducts should return a list of products'(){
        given:
            List<ProductInfo> expectedProductInfos = new ArrayList<>()
            expectedProductInfos.add(new ProductInfo("Sainsbury's Avocado, Ripe & Ready x2","3kb", 1.80,"Avocados"))
            expectedProductInfos.add(new ProductInfo("Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)","3kb", 1.50,"Conference"))
            expectedProductInfos.add(new ProductInfo("Sainsbury's Kiwi Fruit, Ripe & Ready x4","3kb", 1.50,"Kiwi"))

        when:
            List<ProductInfo> actualProductInfos = testObj.getProducts("http://localhost:9090/sainsburyhome.html")

        then:
            actualProductInfos*.collect {it.title} == expectedProductInfos*.collect {it.title} //asserting actual and expected title list
            actualProductInfos*.collect {it.size} == expectedProductInfos*.collect {it.size} //asserting actual and expected size list
            actualProductInfos*.collect {it.unitPrice} == expectedProductInfos*.collect {it.unitPrice} //asserting actual and expected unitPrice list
            actualProductInfos*.collect {it.description} == expectedProductInfos*.collect {it.description} //asserting actual and expected description list

    }

    /**
     * server setup to test getting the html page correctly
     */
    private void startServer(){
        server = new Server(9090)
        server.setStopAtShutdown(true)
        WebAppContext webAppContext = new WebAppContext()
        webAppContext.setContextPath("/")
        webAppContext.setResourceBase("src/test/resources")
        webAppContext.setClassLoader(ProductInfoDiscoveryServiceSpec.class.getClassLoader())
        server.addHandler(webAppContext)
        server.start()
    }
}
