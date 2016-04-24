package android.nla.org.gotway.ui.fragment;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class DisconnDialog
  extends DialogFragment
{
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    paramBundle = new Builder(getActivity());
    View localView = LayoutInflater.from(getActivity()).inflate(2130903048, null);
    localView.findViewById(2131361806).setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ((MainActivity)DisconnDialog.this.getActivity()).disconnect();
        DisconnDialog.this.dismiss();
      }
    });
    localView.findViewById(2131361807).setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DisconnDialog.this.dismiss();
      }
    });
    paramBundle = paramBundle.setView(localView).create();
    paramBundle.setCanceledOnTouchOutside(false);
    paramBundle.setCancelable(false);
    return paramBundle;
  }
}