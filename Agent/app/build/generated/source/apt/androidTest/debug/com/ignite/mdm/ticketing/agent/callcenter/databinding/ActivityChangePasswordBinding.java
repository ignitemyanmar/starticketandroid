package com.ignite.mdm.ticketing.agent.callcenter.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
import android.view.View;
public class ActivityChangePasswordBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.activity_change_password, 1);
        sViewsWithIds.put(R.id.old_password, 2);
        sViewsWithIds.put(R.id.confirm, 3);
        sViewsWithIds.put(R.id.new_password, 4);
        sViewsWithIds.put(R.id.change, 5);
    }
    // views
    public final android.widget.LinearLayout activityChangePassword;
    public final android.widget.Button change;
    public final com.rengwuxian.materialedittext.MaterialEditText confirm;
    private final android.widget.ScrollView mboundView0;
    public final com.rengwuxian.materialedittext.MaterialEditText newPassword;
    public final com.rengwuxian.materialedittext.MaterialEditText oldPassword;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityChangePasswordBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.activityChangePassword = (android.widget.LinearLayout) bindings[1];
        this.change = (android.widget.Button) bindings[5];
        this.confirm = (com.rengwuxian.materialedittext.MaterialEditText) bindings[3];
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.newPassword = (com.rengwuxian.materialedittext.MaterialEditText) bindings[4];
        this.oldPassword = (com.rengwuxian.materialedittext.MaterialEditText) bindings[2];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
        }
        return false;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityChangePasswordBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityChangePasswordBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityChangePasswordBinding>inflate(inflater, com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_change_password, root, attachToRoot, bindingComponent);
    }
    public static ActivityChangePasswordBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityChangePasswordBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_change_password, null, false), bindingComponent);
    }
    public static ActivityChangePasswordBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityChangePasswordBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_change_password_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityChangePasswordBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}