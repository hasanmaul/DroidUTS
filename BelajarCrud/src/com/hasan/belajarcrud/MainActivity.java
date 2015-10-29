package com.hasan.belajarcrud;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	Button tambah;
	private ProgressDialog pDialog;
	Button kembali;
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> listsiswa1;
	
	private static String url_semua_siswa = "http://192.168.1.148/belajarcrud/getAllSiswa.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SISWA = "siswa";
	private static final String TAG_ID = "id";
	private static final String TAG_NISN = "nisn";
	private static final String TAG_NAMA = "nama";
	private static final String TAG_KELAS = "kelas";
	private static final String TAG_ALAMAT = "alamat";
	
	JSONArray listsiswa = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listsiswa1 = new ArrayList<HashMap<String, String>>();
		new LoadSemuaDataSiswa().execute();
		tambah = (Button) findViewById(R.id.btnTambah);
		tambah.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, TambahActivity.class);
				startActivity(i);
				
			}
		});
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			public void OnItemClick(AdapterView<?> parent, View view, int position, long pid) {
				String id = ((TextView) view.findViewById(R.id.textID)).getText().toString();
				Intent edit = new Intent(getApplicationContext(), EditDataActivity.class);
				edit.putExtra(TAG_ID, id);
				startActivityForResult(edit, 100);
			}
			
		});
	}
	
	class LoadSemuaDataSiswa extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Mohon tunggu, memuat data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHTTPRequest(url_semua_siswa, "GET", param);
			Log.d("Semua Daftar Makanan", json.toString());
			try {
				int success = json.getInt(TAG_SUCCESS);
				if(success == 1) {
					listsiswa = json.getJSONArray(TAG_SISWA);
					for(int i = 0; i < listsiswa.length(); i++) {
						JSONObject c = listsiswa.getJSONObject(i);
						String id = c.getString(TAG_ID);
						String nisn = c.getString(TAG_NISN);
						String nama = c.getString(TAG_NAMA); 
						String kelas = c.getString(TAG_KELAS);
						String alamat = c.getString(TAG_ALAMAT);
						
						HashMap<String, String> map = new HashMap<String, String>();
						
						map.put(TAG_ID, id);
						map.put(TAG_NISN, nisn);
						map.put(TAG_NAMA, nama);
						map.put(TAG_KELAS, kelas);
						map.put(TAG_ALAMAT, alamat);
						
						listsiswa1.add(map);
					}
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
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					ListAdapter adapter = new SimpleAdapter(MainActivity.this, listsiswa1, R.layout.komponendata, new String[] {TAG_ID, TAG_NISN, TAG_NAMA, TAG_KELAS, TAG_ALAMAT}, new int[] {R.id.textID, R.id.textNISN, R.id.textNAMA, R.id.textKELAS, R.id.textALAMAT});
					setListAdapter(adapter);
				}
			});
		}
	}
}