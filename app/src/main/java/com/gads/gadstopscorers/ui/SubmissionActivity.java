package com.gads.gadstopscorers.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gads.gadstopscorers.R;
import com.gads.gadstopscorers.network.SubmissionApi;
import com.gads.gadstopscorers.network.SubmissionClient;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.firstNameEditText) EditText mFirstNameEditText;
    @BindView(R.id.secondNameEditText) EditText mSecondNameEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.projectEditText) EditText mProjectEditText;
    @BindView(R.id.submitDetailsButton) Button mSubmitDetailsButton;

    @BindView(R.id.backToMainActivityButton) ImageView mBackToActivityImageButton;

    String mFirstName;
    String mSecondName;
    String mEmailText;
    String mGitHubProjectLink;

    private Dialog mDialog;
    private Dialog mSuccessDialog;
    private Dialog mFailureDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        ButterKnife.bind(this);

        mDialog = new Dialog(SubmissionActivity.this);
        mSuccessDialog = new Dialog(SubmissionActivity.this);
        mFailureDialog = new Dialog(SubmissionActivity.this);

        mSubmitDetailsButton.setOnClickListener(this);
        mBackToActivityImageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == mBackToActivityImageButton){
            Intent backToMainActivityIntent = new Intent(SubmissionActivity.this, MainActivity.class);
            startActivity(backToMainActivityIntent);
        }
        if (view == mSubmitDetailsButton){
            mFirstName = mFirstNameEditText.getText().toString().trim();
            mSecondName = mSecondNameEditText.getText().toString().trim();
            mEmailText = mEmailEditText.getText().toString().trim();
            mGitHubProjectLink = mProjectEditText.getText().toString().trim();

            hideKeyBoard(mSubmitDetailsButton);

            boolean isValidFirstName = checkIfValidFirstName(mFirstName);
            boolean isValidSecondName = checkIfValidSecondName(mSecondName);
            boolean isValidEmail = checkIfValidEmail(mEmailText);
            boolean isValidGitHubLink = checkIfValidGitHubLink(mGitHubProjectLink);
            if(!isValidEmail || !isValidFirstName || !isValidSecondName || !isValidGitHubLink) return;
            showConfirmDialog(mFirstName, mSecondName, mEmailText, mGitHubProjectLink);
        }

    }

    private void hideKeyBoard(Button mSubmitDetailsButton) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(mSubmitDetailsButton.getApplicationWindowToken(),0);
    }

    private boolean checkIfValidEmail(String emailText) {
        if (emailText.equals("")){
            mEmailEditText.setError("Email field cannot be empty");
            return false;
        }
        if (!emailText.matches(String.valueOf(Patterns.EMAIL_ADDRESS))){
            mEmailEditText.setError("Enter a valid Email Address");
            return false;
        }
        return true;
    }

    private boolean checkIfValidGitHubLink(String gitHubProjectLink) {
        if (gitHubProjectLink.equals("")){
            mProjectEditText.setError("Github link cannot be empty");
            return false;
        }
        if(!gitHubProjectLink.contains("github")){
            mProjectEditText.setError("Enter a Valid Github link");
            return false;
        }
        return true;
    }

    private boolean checkIfValidSecondName(String secondName) {
        if (secondName.equals("")){
            mSecondNameEditText.setError("This Second Name field cannot be empty");
            return false;
        }
        return true;
    }

    private boolean checkIfValidFirstName(String firstName) {
        if (firstName.equals("")){
            mFirstNameEditText.setError("The First Name field cannot be empty");
            return false;
        }
        return true;
    }

    private void showConfirmDialog(String firstName, String secondName, String emailAddress, String gitHubProjectLink) {
        mDialog.setContentView(R.layout.dialog_confirm_layout);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        ImageView cancelConfirmDialogButton = mDialog.getWindow().findViewById(R.id.cancelDialogSuccessImageView);
        cancelConfirmDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        Button confirmSubmissionButton = mDialog.getWindow().findViewById(R.id.yesButton);
        confirmSubmissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSendingAction(firstName, secondName, emailAddress, gitHubProjectLink);
            }
        });
    }

    private void startSendingAction(String firstName, String lastName, String emailAddress, String gitHubProjectLink) {
        SubmissionApi client = SubmissionClient.getClient();
        Call<Void> call = client.submitDetails(firstName, lastName, emailAddress, gitHubProjectLink);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SubmissionActivity.this, "Successfully Submitted", Toast.LENGTH_LONG).show();
                    mSuccessDialog.setContentView(R.layout.dialog_success_layout);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mFailureDialog.setContentView(R.layout.dialog_fail_layout);
                Objects.requireNonNull(mFailureDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mFailureDialog.show();
            }
        });
    }


}
