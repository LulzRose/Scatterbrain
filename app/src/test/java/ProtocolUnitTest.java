/*
 * Performs tests on the scatterbrain protocol outside android to
 * reduce the chance of bugged out packets.
 **/
import net.ballmerlabs.scatterbrain.network.AdvertisePacket;
import net.ballmerlabs.scatterbrain.network.BlockDataPacket;
import net.ballmerlabs.scatterbrain.network.DeviceProfile;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@SuppressWarnings("unused")
public class ProtocolUnitTest {

    @SuppressWarnings("unused")
    @Test
    public void AdvertisePacketFromProfileIsValid() {
        byte[] test = {1,2,3,4,5,6};
        DeviceProfile profile = new DeviceProfile(DeviceProfile.deviceType.ANDROID, DeviceProfile.MobileStatus.MOBILE,
                DeviceProfile.HardwareServices.BLUETOOTH, test);
        AdvertisePacket ap  = new AdvertisePacket(profile);

        assertThat(ap.isInvalid() , is(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void AdvertisePacketFromDataAndProfileIsValid() {
        byte[] test = {1,2,3,4,5,6};
        DeviceProfile profile = new DeviceProfile(DeviceProfile.deviceType.ANDROID, DeviceProfile.MobileStatus.MOBILE,
                DeviceProfile.HardwareServices.BLUETOOTH, test);
        AdvertisePacket ap  = new AdvertisePacket(profile);
        byte[] data = ap.getContents();
        AdvertisePacket newpacket = new AdvertisePacket(data);

        System.out.println("AdvertisePacketFromDataAndProfileIsValid");
        for(byte b : newpacket.err)  {
            System.out.print(b + " ");
        }
        System.out.println();
        assertThat(newpacket.isInvalid(), is(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void BlockDataPacketIsValid() {
        byte[] senderluid = {1,2,3,4,5,6};
        byte[] randomdata = {4,2,26,2,6,46,2,2,6,21,6,5,1,7,1,7,1,87,2,78,2,
                4,2,26,2,6,46,2,2,6,21,6,5,1,7,1,7,1,87,2,78,2};
        BlockDataPacket bd = new BlockDataPacket(randomdata, false,false, senderluid);
        assertThat(bd.isInvalid(), is(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void BlockDataPacketHandlesNullData() {
        byte[] senderluid = {1,2,3,4,5,6};
        byte[] randomdata = {};
        BlockDataPacket bd = new BlockDataPacket(randomdata, false, true, senderluid);
        assertThat(bd.isInvalid(), is(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void BlockDataPacketFromDataIsValid() {
        byte[] senderluid = {1,2,3,4,5,6};
        byte[] randomdata = {4,2,26,2,6,46,2,2,6,21,6,5,1,7,1,7,1,87,2,78,2,
                4,2,26,2,6,46,2,2,6,21,6,5,1,7,1,7,1,87,2,78,2};
        BlockDataPacket bd = new BlockDataPacket(randomdata, false, false, senderluid);
        BlockDataPacket ne = new BlockDataPacket(bd.getContents());

        System.out.println("BlockDataPacketFromDataIsValid() err");
        for(int x : ne.err) {
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.println(new String(bd.body));
        System.out.println(new String(ne.body));
        assertThat(ne.isInvalid(), is(false));
    }


    @SuppressWarnings("unused")
    @Test
    public void BlockDataPacketHasSameHashWhenReconstructed() {
        byte[] senderluid = {1,2,3,4,5,6};
        byte[] randomdata = {4,2,26,2,6,46,2,2,6,21,6,5,1,7,1,7,1,87,2,78,2,
                4,2,26,2,6,46,2,2,6,21,6,5,1,7,1,7,1,87,2,78,2};
        BlockDataPacket bd = new BlockDataPacket(randomdata, false,false, senderluid);
        BlockDataPacket ne = new BlockDataPacket(bd.getContents());

        assertThat(bd.getHash().equals(ne.getHash()), is(true));
    }

    @SuppressWarnings("unused")
    @Test
    public void AdvertisePacketIsInvalidWithBogusLUID() {
        byte[] test = {1, 2, 3, 4};
        DeviceProfile profile = new DeviceProfile(DeviceProfile.deviceType.ANDROID, DeviceProfile.MobileStatus.MOBILE,
                DeviceProfile.HardwareServices.BLUETOOTH, test);
        AdvertisePacket ap = new AdvertisePacket(profile);

        byte[] test2 = {1, 2, 3, 4, 8};
        DeviceProfile profile2 = new DeviceProfile(DeviceProfile.deviceType.ANDROID, DeviceProfile.MobileStatus.MOBILE,
                DeviceProfile.HardwareServices.BLUETOOTH, test2);
        AdvertisePacket ap2 = new AdvertisePacket(profile2);

        assertThat(ap.isInvalid() && ap2.isInvalid(), is(true));
    }

    @SuppressWarnings("unused")
    @Test
    public void BlockDataPacketWithNullDataIsValid() {
        byte[] senderluid = {1,2,3,4,5,6};
        byte[] randomdata = {};
        BlockDataPacket bd = new BlockDataPacket(randomdata, false, false,  senderluid);
        BlockDataPacket ne = new BlockDataPacket(bd.getContents());
        if(bd.getContents().length == BlockDataPacket.HEADERSIZE) {
            System.out.println("HEADERSIZE");
        }
        System.out.println(bd.getContents().length);
        System.out.println(ne.getContents().length);
        System.out.println("err");
        for(int b : ne.err) {
            System.out.print(b + " ");
        }
        System.out.println();
        assertThat(bd.getHash().equals(ne.getHash()), is(true));
    }

    @SuppressWarnings("unused")
    @Test
    public void BlockDataSizeOperatorReturnsCorrectSize() {
        byte[] senderluid = {1,2,3,4,5,6};
        byte[] randomdata = {};
        BlockDataPacket bd = new BlockDataPacket(randomdata, false, false, senderluid);

        assertThat(bd.size == BlockDataPacket.getSizeFromData(bd.getContents()), is(true));

        byte[] senderluid2 = {1,2,3,4,5,6};
        byte[] randomdata2 = {3,3,65,34,6,3,52,52,5,2,5};

        BlockDataPacket bd2 = new BlockDataPacket(randomdata2, false, false,  senderluid2);

        assertThat(bd2.size == BlockDataPacket.getSizeFromData(bd2.getContents()), is(true));
    }

}
