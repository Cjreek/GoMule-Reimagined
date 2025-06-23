package gomule.d2i;

import com.google.common.io.BaseEncoding;
import gomule.item.D2Item;
import gomule.util.D2BitReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import randall.d2files.D2TxtFile;

import java.io.File;
import java.nio.file.Files;

import static gomule.d2i.D2SharedStash.D2SharedStashPane;
import static gomule.item.D2ItemTest.HEALTH_POT;
import static gomule.item.D2ItemTest.SMALL_CHARM;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class D2SharedStashWriterTest {

    private static final String EMPTY_STASH =
            "55AA55AA0000000063000000000000004400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004A4D0000";
    private static final String STASH_WITH_POTION =
            "55AA55AA0000000063000000000000004E00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004A4D01001004A0080588144FB400";
    private static final String STASH_WITH_POTION_AND_CHARM =
            "55AA55AA0000000063000000000000006400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004A4D02001004A0080588144FB40010008000059054D84FD88E840E0B50B00C00B478F61F";
    private static final String STASH_WITH_3_POTIONS =
            "55AA55AA0000000063000000000000006200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004A4D03001004A0080580144FB4001004A00805C0144FB4001004A0080500154FB400";
    private static final String STASH_WITH_EXTRA_JM_IN_ITEM =
            "55AA55AA0100000063000000E9280000C001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004A4D0D0010008000051CF4FCD890C739BFC5B522FA972E23B230CB312C67A44271A138412EC5B315DD949C9F0A6644121E1EFF011000800005A454D86D601A024A4D5210C7634C2004CCE63F1000800005A014DD00C2DA1FA26592160056A1CBE83F100080000518F4FCD8C07F16B557A560189048910DCADD7F100080000558F4FCD81038B54371B5B1DA1727F5922E2A04002209F44E94CB52FF01100880040500142B68B30265083D21070A4886F90FA0244049406940E9CAA3CBAD4E204F5D3D2C2F9D79FF011000A0003500E07C6F031000A0003504E07CFB001000A0003508E07C3E011000800005DC14DD000249EAC13197026856A105B82EA3FF1008800005D815DE173C2124B3B702CB7038A0FF1000800005D0151E0A2A8288A45681746462FC07100080000588D5AF316431F2F854053221FE031000C0000500763E0DC2C2CC63553103B27171E104B914E456906B41EE3F100080000500D5CB1AE29E4C11B9029913FF011000800005E05458267899D3FD59A900E0300094FC07";

    @BeforeEach
    public void setup() {
        D2TxtFile.constructTxtFiles("./d2111");
    }

    @Test
    public void writeReplacingSingleItem() throws Exception {
        byte[] simpleStash = BaseEncoding.base16().decode(EMPTY_STASH + EMPTY_STASH + EMPTY_STASH);
        D2SharedStash stash = new D2SharedStash(
                "",
                asList(
                        D2SharedStashPane.fromItems(emptyList(), 0),
                        D2SharedStashPane.fromItems(singletonList(loadItem(HEALTH_POT)), 0),
                        D2SharedStashPane.fromItems(emptyList(), 0)),
                simpleStash);
        byte[] expected = BaseEncoding.base16().decode(EMPTY_STASH + STASH_WITH_POTION + EMPTY_STASH);
        runTest(simpleStash, stash, expected);
    }

    @Test
    public void writeItemsOfDifferentSizes() throws Exception {
        byte[] simpleStash = BaseEncoding.base16().decode(EMPTY_STASH + EMPTY_STASH + EMPTY_STASH);
        D2SharedStash stash = new D2SharedStash(
                "",
                asList(
                        D2SharedStashPane.fromItems(emptyList(), 0),
                        D2SharedStashPane.fromItems(asList(loadItem(HEALTH_POT), loadItem(SMALL_CHARM, 4, 2)), 0),
                        D2SharedStashPane.fromItems(emptyList(), 0)),
                simpleStash);
        byte[] expected = BaseEncoding.base16().decode(EMPTY_STASH + STASH_WITH_POTION_AND_CHARM + EMPTY_STASH);
        runTest(simpleStash, stash, expected);
    }

    @Test
    public void writeReplacingMultipleItems() throws Exception {
        byte[] simpleStash = BaseEncoding.base16().decode(EMPTY_STASH + EMPTY_STASH + EMPTY_STASH);
        D2SharedStash stash = new D2SharedStash(
                "",
                asList(
                        D2SharedStashPane.fromItems(emptyList(), 0),
                        D2SharedStashPane.fromItems(
                                asList(
                                        loadItem(HEALTH_POT, 0, 2),
                                        loadItem(HEALTH_POT, 0, 3),
                                        loadItem(HEALTH_POT, 0, 4)),
                                0),
                        D2SharedStashPane.fromItems(emptyList(), 0)),
                simpleStash);
        byte[] expected = BaseEncoding.base16().decode(EMPTY_STASH + STASH_WITH_3_POTIONS + EMPTY_STASH);
        runTest(simpleStash, stash, expected);
    }

    @Test
    public void writeReplacingAllItems() throws Exception {
        byte[] simpleStash = BaseEncoding.base16().decode(EMPTY_STASH + EMPTY_STASH + EMPTY_STASH);
        D2SharedStash stash = new D2SharedStash(
                "",
                asList(
                        D2SharedStashPane.fromItems(singletonList(loadItem(HEALTH_POT)), 0),
                        D2SharedStashPane.fromItems(singletonList(loadItem(HEALTH_POT)), 0),
                        D2SharedStashPane.fromItems(singletonList(loadItem(HEALTH_POT)), 0)),
                simpleStash);
        byte[] expected = BaseEncoding.base16().decode(STASH_WITH_POTION + STASH_WITH_POTION + STASH_WITH_POTION);
        runTest(simpleStash, stash, expected);
    }

    @Test
    public void roundTripWithExtraJms() throws Exception {
        byte[] stashBytes = BaseEncoding.base16().decode(EMPTY_STASH + EMPTY_STASH + STASH_WITH_EXTRA_JM_IN_ITEM);
        D2SharedStash stash =
                new D2SharedStashReader().readStash("JMExampleStash.d2i", new D2BitReader(stashBytes.clone()));
        runTest(stashBytes, stash, stashBytes.clone());
    }

    private void runTest(byte[] originalStashBytes, D2SharedStash stash, byte[] expected) throws Exception {
        File tempFile = File.createTempFile("d2SharedStashWriterTest", null);
        D2SharedStashWriter writer = new D2SharedStashWriter(tempFile, originalStashBytes);
        writer.write(stash);
        byte[] actual = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(expected, actual);
        D2SharedStash readBackStash = new D2SharedStashReader().readStash("foo", new D2BitReader(actual));
        assertEquals(stash.getPanes().size(), readBackStash.getPanes().size());
        for (int i = 0; i < stash.getPanes().size(); i++) {
            D2SharedStashPane pane = stash.getPane(i);
            D2SharedStashPane readBackPane = readBackStash.getPane(i);
            assertEquals(pane.getGold(), readBackPane.getGold());
            assertEquals(pane.getItems().size(), readBackPane.getItems().size());
            for (int j = 0; j < pane.getItems().size(); j++) {
                assertArrayEquals(
                        pane.getItems().get(j).get_bytes(),
                        readBackPane.getItems().get(j).get_bytes());
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private D2Item loadItem(byte[] b) throws Exception {
        return loadItem(b, 2, 2);
    }

    private D2Item loadItem(byte[] b, int col, int row) throws Exception {
        D2Item item = new D2Item("foo", new D2BitReader(b), 75);
        item.set_col((short) col);
        item.set_row((short) row);
        item.set_location((short) 0);
        item.set_body_position((short) 0);
        item.set_panel((short) 5);
        return item;
    }
}
