package cn.com.codeteenager.accessibilityapp;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;


public class MyAccessibilityService extends AccessibilityService {

    private final String INSTALL_AND_UNINSTALL = "com.android.packageinstaller";
    private String[] key = new String[]{"卸载", "安装", "继续", "继续安装", "替换", "下一步", "仅允许一次", "完成", "确定"};

    /**
     * 当服务连接成功时
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(MyAccessibilityService.this, "服务连接成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 当指定事件发出服务时
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        System.out.println("窗口事件的包名" + event.getPackageName().toString());

        if (event == null || !event.getPackageName().toString()
                .contains("packageinstaller"))//不写完整包名，是因为某些手机(如小米)安装器包名是自定义的
            return;

        if (event.getSource() != null) {
            switch (event.getPackageName().toString()) {
                case INSTALL_AND_UNINSTALL:
                    for (int i = 0; i < key.length; i++) {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                        if (nodeInfo != null) {
                            List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByText(key[i]);
                            System.out.println("节点的个数" + nodes.size());
                            if (nodes != null && !nodes.isEmpty()) {
                                AccessibilityNodeInfo node;
                                for (int j = 0; j < nodes.size(); j++) {
                                    node = nodes.get(j);
                                    System.out.println("节点的类名" + node.getClassName().toString());
                                    if ((node.getClassName().equals("android.widget.Button") || node.getClassName().equals("android.widget.TextView")) && node.isEnabled()) {
                                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 终止服务时调用
     */
    @Override
    public void onInterrupt() {

    }
}
