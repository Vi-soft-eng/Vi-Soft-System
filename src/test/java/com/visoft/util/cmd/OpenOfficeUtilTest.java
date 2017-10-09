package com.visoft.util.cmd;

import com.visoft.AbstractTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class OpenOfficeUtilTest extends AbstractTest {

    @Autowired
    OpenOfficeUtil openOfficeUtil;

    @Value("${open.office.process.name}")
    private String openOfficeProcessName;

    private final String KILL_PROCESS_COMMAND_PREFIX = "TASKKILL /F /IM ";

    @After
    public void closeOpenOfficeProcess() {
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec(KILL_PROCESS_COMMAND_PREFIX + openOfficeProcessName);
            LOG.info("The process " + openOfficeProcessName + " was destroyed.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
//    @Ignore
    public void runOpenOfficeTest() {
        try {
            openOfficeUtil.runOpenOffice();
            Assert.assertTrue(openOfficeUtil.isOpenOfficeRunning());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void runOpenOfficeMockTest() throws IOException, InterruptedException {

        OpenOfficeUtil openOfficeUtil = mock(OpenOfficeUtil.class);
        when(openOfficeUtil.isOpenOfficeRunning()).thenReturn(true);

        doAnswer(answer(openOfficeUtil)).when(openOfficeUtil).runOpenOffice();

        openOfficeUtil.runOpenOffice();

        verify(openOfficeUtil, times(1)).isOpenOfficeRunning();

    }

    private Answer<String> answer(final OpenOfficeUtil openOfficeUtil) {
        return new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                openOfficeUtil.isOpenOfficeRunning();
                return null;
            }
        };
    }

}
