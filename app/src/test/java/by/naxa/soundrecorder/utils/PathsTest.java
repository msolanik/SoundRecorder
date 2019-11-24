package by.naxa.soundrecorder.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import by.naxa.soundrecorder.utils.util.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PathsTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void combineTest() {
         String rootPath = temporaryFolder.getRoot().toString();
         String path = Paths.combine(temporaryFolder.getRoot(), "first", "second");
         assertEquals(rootPath + File.separator + "first" + File.separator +
                 "second", path);
    }

    @Test
    public void combineTest_emptyChild() {
        String rootPath = temporaryFolder.getRoot().toString();
        String path = Paths.combine(temporaryFolder.getRoot(), "", "second");
        assertEquals(rootPath + File.separator + "second", path);
    }

    @Test
    public void createDirectoryTest() {
        Paths.createDirectory(temporaryFolder.getRoot(), "first", "second");

        File firstFile = new File(temporaryFolder.getRoot() + File.separator + "first");
        assertTrue(firstFile.exists());

        File secondFile = new File(temporaryFolder.getRoot().toString() + File.separator + "first"
         + File.separator + "second");
        assertTrue(secondFile.exists());
    }

    @Test
    public void createDirectoryTest_emptyChild() {
        Paths.createDirectory(temporaryFolder.getRoot(), "", "second");

        File file = new File(temporaryFolder.getRoot().toString() + File.separator + "second");
        assertTrue(file.exists());
    }
}
