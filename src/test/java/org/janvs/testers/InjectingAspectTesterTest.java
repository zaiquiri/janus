package org.janvs.testers;

import org.janvs.injectors.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InjectingAspectTesterTest {

    @Mock
    private Tester tester;
    @Mock
    private Injector injector;

    @Test
    public void shouldInjectInstanceAndCallRunWhenRun() {
        new InjectingAspectTester(tester, injector).run();

        verify(injector).injectInstance();
        verify(tester).run();
    }
}
