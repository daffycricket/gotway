package android.nla.org.gotway.ui.fragment;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.nla.org.gotway.R;
import android.nla.org.gotway.ui.activity.MainActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class DisconnDialog
        extends DialogFragment {
    public Dialog onCreateDialog(Bundle paramBundle) {
        Builder builder = new Builder(getActivity());
        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.frgament_disconn_dialog, null);
        localView.findViewById(R.id.yes).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                ((MainActivity) DisconnDialog.this.getActivity()).disconnect();
                DisconnDialog.this.dismiss();
            }
        });
        localView.findViewById(R.id.no).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DisconnDialog.this.dismiss();
            }
        });
        Dialog dialog = builder.setView(localView).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }
}