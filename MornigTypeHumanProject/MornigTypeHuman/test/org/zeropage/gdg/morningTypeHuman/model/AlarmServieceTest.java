package org.zeropage.gdg.morningTypeHuman.model;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import org.zeropage.gdg.morningTypeHuman.view.LaunchActivity;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class AlarmServieceTest extends ActivityInstrumentationTestCase2<LaunchActivity>{

    private Context contenxt;

    public AlarmServieceTest() {
        super(LaunchActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        contenxt = getActivity();
        super.setUp();
    }

    public void testAlarm() throws Exception {
        fail(); // 이런건 테스트를 어떻게 짜야할지 모르겠다. 일단 없어도 짤 수 있을 것 같으니 그냥 간다!.
        // given
        AlarmService as = new AlarmService();

        // when

        // then
    }
}
