package TestPackage;

import LibraryFile.ConstantVaribales;
import LibraryFile.ReusableMethodsClass;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class ClusterAPITest implements ConstantVaribales {
    Properties prp;
    String payload;

    @BeforeClass
    public void commonService() throws IOException
    {
        prp = ReusableMethodsClass.getPropertyFile();

    }

    private void preConditionSet(String host, String clusterHost, String payLoadPath) throws IOException
    {
        if(payLoadPath!="")
        {
            payload = ReusableMethodsClass.generateStringFromResources(payLoadPath);
            payload = payLoadPath.replaceAll("<CLUSTER_HOST>",prp.getProperty(clusterHost)).replaceAll("<HANDSHAKE_KEY>","LeNoVo_"+prp.getProperty(clusterHost));
        }
        prp = ReusableMethodsClass.getPropertyFile();
        RestAssured.baseURI= prp.getProperty(host);

    }

    @Test
    public void postRestoreCluster() {
        try{

            preConditionSet("CLUSTER_BASE_URI","CLUSTER_HOST" ,"Resources/restoreCluster.json");

            given().
                    header("Content-Type","application/json").
                    body(payload).

                    when().
                    post("/restore_service").

                    then().assertThat().
                    statusCode(STATUS_OK).log().all();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail("Execution failed due to exception in Post Restore Cluster Method!");
        }

    }

    @Test
    public void postAddNodeService() {
        try{

            preConditionSet("CLUSTER_BASE_URI","CLUSTER_HOST" ,"Resources/addNodeService.json");

            given().
                    header("Content-Type","application/json").
                    body(payload).

                    when().
                    post("/addnode_service").

                    then().assertThat().
                    statusCode(STATUS_OK).log().all();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail("Execution failed due to exception in add Node Services");
        }

    }

    @Test
    public void postRemoveNodeService() {
        try{

            preConditionSet("CLUSTER_BASE_URI","CLUSTER_HOST" ,"Resources/removeNodeService.json");

            given().
                    header("Content-Type","application/json").
                    body(payload).

                    when().
                    post("/removenode_service").

                    then().assertThat().
                    statusCode(STATUS_OK).log().all();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail("Execution failed due to exception in remove Node Services");
        }

    }


    @Test
    public void postBackupService() {
        try{

            preConditionSet("CLUSTER_BASE_URI","CLUSTER_HOST" ,"Resources/backupService.json");

            given().
                    header("Content-Type","application/json").
                    body(payload).

                    when().
                    post("/backup_service").

                    then().assertThat().
                    statusCode(STATUS_OK).log().all();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail("Execution failed due to exception in backup Services");
        }

    }

    @Test
    public void postTerminateCluster() {
        try{

            preConditionSet("CLUSTER_BASE_URI","CLUSTER_HOST" ,"Resources/terminateCluster.json");

            given().
                    header("Content-Type","application/json").
                    body(payload).

                    when().
                    post("/terminate_cluster").

                    then().assertThat().
                    statusCode(STATUS_OK).log().all();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail("Execution failed due to exception in terminate cluster Services");
        }

    }



}
