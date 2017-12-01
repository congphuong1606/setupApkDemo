package com.congp.app.data;
public class CauHoi {

	private int mId;
	private String mSoChuong;
	private String mCauHoi;
	private String mDapAn;
	private String mCauA;
	private String mCauB;
	private String mCauC;
	private String mCauD;
	private String mCauE;
	private String mGiaiThich;
	private int mluaChon;

	public CauHoi( int mId) {
		this.mId = mId;
	}



	public CauHoi(int mId, String mSoChuong, String mCauHoi,
				  String mDapAn, String mCauA, String mCauB,
				  String mCauC, String mCauD, String mCauE, int mLuaChon) {
		this.mId = mId;
		this.mSoChuong = mSoChuong;
		this.mCauHoi = mCauHoi;
		this.mDapAn = mDapAn;
		this.mCauA = mCauA;
		this.mCauB = mCauB;
		this.mCauC = mCauC;
		this.mCauD = mCauD;
		this.mCauE = mCauE;
		this.mluaChon=mLuaChon;

	}

	public CauHoi(int mId, String mSoChuong, String mCauHoi, String mDapAn, String mCauA, String mCauB, String mCauC, String mCauD, String mCauE, String mGiaiThich) {
		this.mId = mId;
		this.mSoChuong = mSoChuong;
		this.mCauHoi = mCauHoi;
		this.mDapAn = mDapAn;
		this.mCauA = mCauA;
		this.mCauB = mCauB;
		this.mCauC = mCauC;
		this.mCauD = mCauD;
		this.mCauE = mCauE;
		this.mGiaiThich = mGiaiThich;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getmSoChuong() {
		return mSoChuong;
	}

	public void setmSoChuong(String mSoChuong) {
		this.mSoChuong = mSoChuong;
	}

	public String getmCauHoi() {
		return mCauHoi;
	}

	public void setmCauHoi(String mCauHoi) {
		this.mCauHoi = mCauHoi;
	}

	public String getmDapAn() {
		return mDapAn;
	}

	public void setmDapAn(String mDapAn) {
		this.mDapAn = mDapAn;
	}

	public String getmCauA() {
		return mCauA;
	}

	public void setmCauA(String mCauA) {
		this.mCauA = mCauA;
	}

	public String getmCauB() {
		return mCauB;
	}

	public void setmCauB(String mCauB) {
		this.mCauB = mCauB;
	}

	public String getmCauC() {
		return mCauC;
	}

	public void setmCauC(String mCauC) {
		this.mCauC = mCauC;
	}

	public String getmCauD() {
		return mCauD;
	}

	public void setmCauD(String mCauD) {
		this.mCauD = mCauD;
	}

	public String getmCauE() {
		return mCauE;
	}

	public void setmCauE(String mCauE) {
		this.mCauE = mCauE;
	}

	public String getmGiaiThich() {
		return mGiaiThich;
	}

	public void setmGiaiThich(String mGiaiThich) {
		this.mGiaiThich = mGiaiThich;
	}

	public int getMluaChon() {
		return mluaChon;
	}

	public void setMluaChon(int mluaChon) {
		this.mluaChon = mluaChon;
	}

}
