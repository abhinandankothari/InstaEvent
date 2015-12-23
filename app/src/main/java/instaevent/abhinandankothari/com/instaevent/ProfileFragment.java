package instaevent.abhinandankothari.com.instaevent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import instaevent.abhinandankothari.com.instaevent.models.User;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        User user = (User) ParseUser.getCurrentUser();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView userName = (TextView) view.findViewById(R.id.user_name);
        TextView location = (TextView) view.findViewById(R.id.location);
        userName.setText(user.getName());
        location.setText(user.getLocation());
        getActivity().setTitle("Profile");
        return view;
    }
}
