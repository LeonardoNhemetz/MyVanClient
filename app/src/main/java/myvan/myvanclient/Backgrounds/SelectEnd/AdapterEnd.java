package myvan.myvanclient.Backgrounds.SelectEnd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import myvan.myvanclient.ActivityPrincipal;
import myvan.myvanclient.R;

/**
 * Created by Leonardo on 15/12/2016.
 */

public class AdapterEnd extends BaseAdapter {

    Context c;
    ArrayList<VariaveisSelectEnd> variaveisSelectEnds;
    LayoutInflater inflater;


    public AdapterEnd(Context c, ArrayList<VariaveisSelectEnd> variaveisSelectMinimos) {
        this.c = c;
        this.variaveisSelectEnds = variaveisSelectMinimos;

        //initialize
        inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return variaveisSelectEnds.size();
    }

    @Override
    public Object getItem(int position) {
        return variaveisSelectEnds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return variaveisSelectEnds.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.modelendir,parent,false);
        }

        TextView end_pass1=(TextView) convertView.findViewById(R.id.rbIrEnd1);
        TextView end_pass2=(TextView) convertView.findViewById(R.id.rbIrEnd2);
        TextView end_pass3=(TextView) convertView.findViewById(R.id.rbVoltarEnd3);
        TextView end_pass4=(TextView) convertView.findViewById(R.id.rbVoltarEnd4);


        end_pass1.setText(variaveisSelectEnds.get(position).getEnd_pass1());
        end_pass2.setText(variaveisSelectEnds.get(position).getEnd_pass2());
        end_pass3.setText(variaveisSelectEnds.get(position).getEnd_pass3());
        end_pass4.setText(variaveisSelectEnds.get(position).getEnd_pass4());



        return convertView;


    }


}
