import org.junit.Test;

import com.cms.utils.DrawCodeUtil;


public class TestDrawCodeUtil {
    @Test
    public void testGenerateCheckcode(){
        for(int i=0;i<100;i++){
            String b=DrawCodeUtil.getInstance().generateCheckcode();
            System.out.println(b);
        }
       
    }
}
