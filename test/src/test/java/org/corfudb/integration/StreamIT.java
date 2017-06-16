package org.corfudb.integration;

import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.view.stream.IStreamView;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A set integration tests that exercise the stream API.
 */

public class StreamIT {
    static String corfuSingleNodeHost;
    static int corfuSingleNodePort;

    @Test
    public void testRemainingUpTo() {
        CorfuRuntime rt = new CorfuRuntime("localhost:9000").connect();

        UUID s1 = CorfuRuntime.getStreamID("StreamA");
        IStreamView sv = rt.getStreamsView().get(s1);

        byte[] data = new byte[4000];

        for(int x = 0; x < 300; x++) {
            sv.append(data);
        }

        rt.getAddressSpaceView().prefixTrim(100);
        rt.getAddressSpaceView().invalidateServerCaches();


        CorfuRuntime rt2 = new CorfuRuntime("localhost:9000").connect();
        UUID s2 = CorfuRuntime.getStreamID("StreamA");
        IStreamView sv2 = rt2.getStreamsView().get(s1);

        List<ILogData> entries = sv2.remainingUpTo(Long.MAX_VALUE);
        System.out.print("a");

    }

}
