package by.naxa.soundrecorder.adapters;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.FieldSetter;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import by.naxa.soundrecorder.DBHelper;
import by.naxa.soundrecorder.RecordingItem;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileViewerAdapter.class, Toast.class})
public class FileViewerAdapterTest {

    private static final String RECORD_TEST_FILE = "testFile";
    private static final int RECORD_ID = 1;
    private static final String NAME = "testName";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private FileViewerAdapter fileViewerAdapter;
    private Context mockedContext;
    private DBHelper mockedDbHelper;
    private LinearLayoutManager mockedLinearLayout;
    private Toast mockedToast;

    private RecordingItem recordingItem;

    @Before
    public void setUp() throws NoSuchFieldException, IOException {
        recordingItem = new RecordingItem();
        recordingItem.setFilePath(temporaryFolder.newFile(RECORD_TEST_FILE).getPath());
        recordingItem.setId(RECORD_ID);
        recordingItem.setName(NAME);

        mockedToast = mock(Toast.class);
        mockedContext = mock(Context.class);
        mockedLinearLayout = mock(LinearLayoutManager.class);
        mockedDbHelper = mock(DBHelper.class);
        fileViewerAdapter = new FileViewerAdapter(mockedContext, mockedLinearLayout);

        Field field = fileViewerAdapter.getClass().getDeclaredField("mDatabase");
        FieldSetter.setField(fileViewerAdapter, field, mockedDbHelper);

        fileViewerAdapter = PowerMockito.spy(fileViewerAdapter);
    }

    @Test
    public void onNewDatabaseEntryAddedTest() {
        doNothing().when(fileViewerAdapter).notifyItemInserted(anyInt());
        when(mockedDbHelper.getCount()).thenReturn(2);

        fileViewerAdapter.onNewDatabaseEntryAdded();

        verify(mockedLinearLayout, times(1)).scrollToPosition(eq(1));
    }

    @Test
    public void removeTest() {
        mockStatic(Toast.class);
        when(mockedContext.getString(anyInt())).thenReturn("TEST");
        when(Toast.makeText(any(Context.class), anyString(), anyInt())).thenReturn(mockedToast);
        doNothing().when(mockedToast).show();
        doNothing().when(fileViewerAdapter).notifyItemRemoved(anyInt());
        when(mockedDbHelper.getItemAt(eq(RECORD_ID))).thenReturn(recordingItem);

        fileViewerAdapter.remove(RECORD_ID);

        verify(mockedDbHelper, times(1)).removeItemWithId(eq(RECORD_ID));

        File file = new File(temporaryFolder.getRoot().toString() + File.separator + RECORD_TEST_FILE);
        assertFalse(file.exists());
    }

    @Test
    public void removeTest_cannotBeDeleted() {
        recordingItem.setFilePath(temporaryFolder.getRoot() + File.separator + "test");

        mockStatic(Toast.class);
        when(mockedContext.getString(anyInt())).thenReturn("TEST");
        when(Toast.makeText(any(Context.class), anyString(), anyInt())).thenReturn(mockedToast);
        doNothing().when(mockedToast).show();
        when(mockedDbHelper.getItemAt(eq(RECORD_ID))).thenReturn(recordingItem);

        fileViewerAdapter.remove(RECORD_ID);

        verify(mockedDbHelper, never()).removeItemWithId(eq(RECORD_ID));
    }
}
