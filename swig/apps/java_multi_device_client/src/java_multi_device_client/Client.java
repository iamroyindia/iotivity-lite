package java_multi_device_client;

import org.iotivity.*;

public class Client {

    static private Thread mainThread;
    static private Thread shutdownHook = new Thread() {
        public void run() {
            System.out.println("Calling main_shutdown.");
            OCMain.mainShutdown();
            mainThread.interrupt();
        }
    };

    public static boolean stopGetPost = false;
    public static GetFridgeResponseHandler getFridgeResponseHandler = new GetFridgeResponseHandler();
    public static PostFridgeResponseHandler postFridge = new PostFridgeResponseHandler();
    public static GetTemperatureResponseHandler getTemperatureResponseHandler = new GetTemperatureResponseHandler();
    public static PostTemperatureResponseHandler postTemperature = new PostTemperatureResponseHandler();

    public static void main(String argv[]) {
        mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        String creds_path = "./multi_device_client_creds/";
        java.io.File directory = new java.io.File(creds_path);
        if (! directory.exists()) {
            directory.mkdir();
        }
        System.out.println("Storage Config PATH : " + directory.getPath());
        if (0 != OCStorage.storageConfig(directory.getPath())) {
            System.err.println("Failed to setup Storage Config.");
        }

        MyInitHandler h = new MyInitHandler();
        int init_ret = OCMain.mainInit(h);
        if (init_ret < 0) {
            System.exit(init_ret);
        }

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
