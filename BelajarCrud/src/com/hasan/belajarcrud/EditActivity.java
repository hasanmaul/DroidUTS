package com.hasan.belajarcrud;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EditActivity extends Activity {
	Button editsimpan, edithapus, editkembali;
	EditText editENISN, editENAMA, editEKELAS, editEALAMAT;
	String id;
	private ProgressDialog pDialog;
	
	JSONParser jsonParser = new JSONParser();
	private static String url_siswa_details = "http://192.168.1.148/belajarcrud/get_siswa_details.php";
	private static String url_update_siswa = "http://192.168.1.148/belajarcrud/update_siswa.php";
	private static String url_delete_siswa = "http://192.168.1.148/belajarcrud/delete_siswa.php";
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SISWA = "siswa";
	private static final String TAG_ID = "id";
	private static final String TAG_NISN = "nisn";
	private static final String TAG_NAMA = "nama";
	private static final String TAG_KELAS = "kelas";
	private static final String TAG_ALAMAT = "alamat";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_data);
		
		Intent i = getIntent();
		id = i.getStringExtra(TAG_ID);
		new getSiswaDetails().execute();
		editsimpan = (Button) findViewById(R.id.btnEDITSIMPAN);
		editsimpan.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new UpdateSiswa().execute();
			}
		});
		edithapus = (Button) findViewById(R.id.btnEDITHAPUS);
		edithapus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DeleteSiswa().execute();
			}
		});
		editkembali = (Button) findViewById(R.id.btnEDITKEMBALI);
		editkembali.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(EditDataActivity.this, MainActivity.class);
				startActivity(i);
			}
		});
	}
	
	class getSiswaDetails extends AsyncTask<String, String, String> 
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditDataActivity.this);
			pDialog.setMessage("Mengambil data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int success;
					try {
						List<NameValuePair> param = new ArrayList<NameValuePair>();
						param.add(new BasicNameValuePair("id", id));
						
						JSONObject json = jsonParser.makeHTTPRequest(url_siswa_details, "GET", param);
						Log.d("Single DAFTARBUKU Details", json.toString());
						
						success = json.getInt(TAG_SUCCESS);
						if(success == 1) {
							JSONArray siswaOBJB = json.getJSONArray(TAG_SISWA);
							
							JSONObject siswa = siswaOBJB.getJSONObject(0);
							editENISN = (EditText) findViewById(R.id.editEDITNISN);
							editENAMA = (EditText) findViewById(R.id.editEDITNAMA);
							editEKELAS = (EditText) findViewById(R.id.editEDITKELAS);
							editEALAMAT = (EditText) findViewById(R.id.editEDITALAMAT);
							
							editENISN.setText(siswa.getString(TAG_NISN));
							editENAMA.setText(siswa.getString(TAG_NAMA));
							editEKELAS.setText(siswa.getString(TAG_KELAS));
							editEALAMAT.setText(siswa.getString(TAG_ALAMAT));
						} else {
							
						}
					} catch(JSONException e) {
						e.printStackTrace();
					}
				}
			}).start();
			return null;
		}
		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
	}
	class UpdateSiswa extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			super.onPreExecute();
			pDialog.setMessage("Mengambil Data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			String nisn = editENISN.getText().toString();
			String nama = editENAMA.getText().toString();
			String kelas = editEKELAS.getText().toString();
			String alamat = editEALAMAT.getText().toString();
			
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			
			param.add(new BasicNameValuePair(TAG_ID, id));
			param.add(new BasicNameValuePair(TAG_NISN, nisn));
			param.add(new BasicNameValuePair(TAG_NAMA, nama));
			param.add(new BasicNameValuePair(TAG_KELAS, kelas));
			param.add(new BasicNameValuePair(TAG_ALAMAT, alamat));
			JSONObject json = jsonParser.makeHTTPRequest(url_update_siswa, "POST", param);
			try {
				int success = json.getInt(TAG_SUCCESS);
				if(success == 1) {
					Intent i = new Intent(EditDataActivity.this, MainActivity.class);
					startActivity(i);
				} else {
					
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
	}
	class DeleteSiswa extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditDataActivity.this);
			pDialog.setMessage("Mengambil data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			int success;
			try {
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("id", id));
				
				JSONObject json = jsonParser.makeHTTPRequest(url_delete_siswa, "POST", param);
				
				Log.d("Hapus Data", json.toString());
				
				success = json_getInt(TAG_SUCCESS);
				if(success == 1) {
					Intent i = new Intent(EditDataActivity.this, MainActivity.class);
					startActivity(i);
				} else {
					
				}
			} catch(JSONException e) {
				e.printStackTrace();
			} return null;
		}
		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
	}
	public int json_getInt(String tagSuccess) {
		// TODO Auto-generated method stub
		return 0;
	}
}