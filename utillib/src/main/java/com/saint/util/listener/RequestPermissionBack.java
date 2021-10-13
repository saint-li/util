package com.saint.util.listener;

import java.util.List;

public interface RequestPermissionBack {
    void onSuccess(List<String> permissions, boolean all);

    void onFailed(List<String> permissions, boolean never);
}
