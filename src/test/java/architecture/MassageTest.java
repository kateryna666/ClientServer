package architecture;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import packege.Massage;
import packege.Packet;
import shop.Command;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class MassageTest {
    @Test
    void massagePrintTest() throws UnsupportedEncodingException {
        System.out.println(new Packet((byte) new Random().nextInt(Byte.MAX_VALUE),
                100, new Massage(Command.ANSWER_OK.ordinal(), 1,
                new JSONObject().put("answer", "OK").toString().getBytes("utf-8"))));
    }
}
