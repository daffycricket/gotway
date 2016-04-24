package android.nla.org.gotway.ui.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.nla.org.gotway.share.SharePeference;
import android.nla.org.gotway.ui.activity.MainActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class ExitDialog
        extends DialogFragment {
    public Dialog onCreateDialog(Bundle paramBundle) {
        Builder builder = new Builder(getActivity());
        View localView = LayoutInflater.from(getActivity()).inflate(2130903049, null);
        final CheckBox localCheckBox = (CheckBox) localView.findViewById(2131361808);

        localView.findViewById(2131361806).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = 2;
                if (localCheckBox.isChecked()) {
                    i = 0x2 | 0x1;
                    ExitDialog.this.getActivity().getSharedPreferences(SharePeference.FILE_NMAE, 0).edit().putInt(SharePeference.EXIT_MODE, i).commit();
                }
                ExitDialog.this.dismiss();
                ((MainActivity) ExitDialog.this.getActivity()).exit(i);
            }
        });

        localView.findViewById(2131361807).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = 0;
                if (localCheckBox.isChecked()) {
                    i = 0x0 | 0x1;
                    ExitDialog.this.getActivity().getSharedPreferences(SharePeference.FILE_NMAE, 0).edit().putInt(SharePeference.EXIT_MODE, i).commit();
                }
                ExitDialog.this.dismiss();
                ((MainActivity) ExitDialog.this.getActivity()).exit(i);
            }
        });

        Dialog dialog = builder.setView(localView).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }
}