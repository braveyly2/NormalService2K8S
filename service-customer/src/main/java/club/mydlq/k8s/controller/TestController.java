package club.mydlq.k8s.controller;

import club.mydlq.k8s.feign.TestService;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class TestController {

    protected static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @GetMapping("/info")
    public String getTestInfo(){
        return testService.getInfo();
    }

    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }

    @GetMapping("/world")
    public String getWorld(){
        try{
            //String kubeConfigPath = "/root/.kube/config";
            String kubeConfigPath = "/config";

            ApiClient client =
                    ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

            //将加载confi的client设置为默认的client
            Configuration.setDefaultApiClient(client);


            //ApiClient client = Config.defaultClient();
            //Configuration.setDefaultApiClient(client);

            CoreV1Api api = new CoreV1Api();
            V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
            for (V1Pod item : list.getItems()) {
                //System.out.println(item.getMetadata().getName());
                logger.info(item.getMetadata().getName());
            }

            V1ServiceList listNamespacedService =
                    api.listNamespacedService(
                            "default",
                            null,
                            null,
                            null,
                            null,
                            null,
                            Integer.MAX_VALUE,
                            null,
                            5000,
                            Boolean.FALSE);
            for (V1Service item : listNamespacedService.getItems()) {
                //System.out.println(item.getMetadata().getName());
                logger.info("service name=" + item.getMetadata().getName());
            }

            V1ConfigMapList listNamespacedConfigMap =
                    api.listNamespacedConfigMap(
                            "kube-system",
                            null,
                            null,
                            null,
                            null,
                            null,
                            Integer.MAX_VALUE,
                            null,
                            5000,
                            Boolean.FALSE);
            for (V1ConfigMap item : listNamespacedConfigMap.getItems()) {
                //System.out.println(item.getMetadata().getName());
                logger.info("configmap name=" + item.getMetadata().getName());
            }

            }catch(Exception e){
            //logger.error(e.fillInStackTrace());
                logger.error(e.toString());
            }
        return "hello";
    }
}
