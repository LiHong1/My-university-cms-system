function openWin(f,n,w,h,s){
	//window.open(url, "", "");
	sb = s == "1" ? "1" : "0";
	l = (screen.width - w)/2;
	t = (screen.height - h)/2;
	if(!w) {
		l=t=0;
		w=screen.width;
		h=screen.height;
	}
	sFeatures = "left="+ l +",top="+ t +",height="+ h +",width="+ w
	+ ",center=1,scrollbars=" + sb + ",status=0,directories=0,channelmode=0";
	openwin = window.open(f , n , sFeatures );
}