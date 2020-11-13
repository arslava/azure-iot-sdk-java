package samples.com.microsoft.azure.sdk.iot;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubConnectionStatusChangeCallback;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DeviceClientManager extends ClientManagerBase {

    private interface DeviceClientNonDelegatedFunction {
        void open();
        void closeNow();
        void registerConnectionStatusChangeCallback(IotHubConnectionStatusChangeCallback callback, Object callbackContext);
    }

    // The methods defined in the interface DeviceClientNonDelegatedFunction will be called on DeviceClientManager, and not on DeviceClient.
    @Delegate(excludes = DeviceClientNonDelegatedFunction.class)
    private final DeviceClient client;

    DeviceClientManager(DeviceClient deviceClient) {
        connectionStatus = ConnectionStatus.DISCONNECTED;
        client = deviceClient;
        client.registerConnectionStatusChangeCallback(this, this);
    }

    @Override
    public void openClient() throws IOException {
        client.open();
    }

    @Override
    public void closeClient() throws IOException {
        client.closeNow();
    }

    @Override
    public String getClientId() {
        return  client.getConfig().getDeviceId();
    }

    public DeviceClient getClient(){
        return client;
    }
}
