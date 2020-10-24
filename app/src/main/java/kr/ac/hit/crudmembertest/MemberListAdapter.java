package kr.ac.hit.crudmembertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MemberListAdapter extends BaseAdapter {
    private Context mContext;

    public MemberListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return MainActivity.memberListDataArray.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.member_list, null);

            ViewHolder memberHolder = new ViewHolder();
            memberHolder.userId = (TextView)view.findViewById(R.id.user_id);
            memberHolder.userPassword = (TextView)view.findViewById(R.id.user_password);
            memberHolder.userName = (TextView)view.findViewById(R.id.user_name);
            memberHolder.userItqid = (TextView)view.findViewById(R.id.user_itqid);
            memberHolder.userSex = (TextView)view.findViewById(R.id.user_sex);
            memberHolder.userHobby = (TextView)view.findViewById(R.id.user_hobby);
            memberHolder.userPhone = (TextView)view.findViewById(R.id.user_phone);
            memberHolder.userHPhone = (TextView)view.findViewById(R.id.user_hphone);
            memberHolder.userZipCode1 = (TextView)view.findViewById(R.id.user_zipcode1);
            memberHolder.userZipCode2 = (TextView)view.findViewById(R.id.user_zipcode2);
            memberHolder.userAddress = (TextView)view.findViewById(R.id.user_address);
            memberHolder.userAddressEtc = (TextView)view.findViewById(R.id.user_address_etc);

            view.setTag(memberHolder);
        }

        try {
            if (MainActivity.memberListDataArray.get(position) != null) {
                ViewHolder viewHolder = (ViewHolder)view.getTag();

                JSONObject jsonObject = MainActivity.memberListDataArray.getJSONObject(position);

                viewHolder.userId.setText("ID : " + jsonObject.getString("id"));
                viewHolder.userPassword.setText("PASSWORD : " + jsonObject.getString("pswd"));
                viewHolder.userName.setText("NAME : " + jsonObject.getString("name"));
                viewHolder.userItqid.setText("ITQID : " + jsonObject.getString("itqid"));
                viewHolder.userSex.setText("SEX : " + jsonObject.getString("sex"));
                viewHolder.userHobby.setText("HOBBY : " + jsonObject.getString("hobby"));
                viewHolder.userPhone.setText("PHONE : " + jsonObject.getString("phone"));
                viewHolder.userHPhone.setText("HPHONE : " + jsonObject.getString("hphone"));
                viewHolder.userZipCode1.setText("ZIPCODE1 : " + jsonObject.getString("zipcode1"));
                viewHolder.userZipCode2.setText("ZIPCODE2 : " + jsonObject.getString("zipcode2"));
                viewHolder.userAddress.setText("ADDRESS1 : " + jsonObject.getString("address"));
                viewHolder.userAddressEtc.setText("ADDRESS2 : " + jsonObject.getString("address_etc"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }

    private static class ViewHolder {
        TextView userId;
        TextView userPassword;
        TextView userName;
        TextView userItqid;
        TextView userSex;
        TextView userHobby;
        TextView userPhone;
        TextView userHPhone;
        TextView userZipCode1;
        TextView userZipCode2;
        TextView userAddress;
        TextView userAddressEtc;
    }
}
