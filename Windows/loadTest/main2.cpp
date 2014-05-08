#include <windows.h>
#include <stdio.h>
#include <string>
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <pdh.h>
#include <PdhMsg.h>
#pragma comment(lib, "pdh.lib")
using namespace std;
std::wstring AppPath()
{
        WCHAR szPath[MAX_PATH];
        GetModuleFileName(NULL, szPath, FILENAME_MAX);
        std::wstring appPath(szPath);
        appPath = appPath.substr(0,appPath.find_last_of(L"\\"));
        return appPath;
}

long GetMajorVersion(){
        OSVERSIONINFO	vi;
        vi.dwOSVersionInfoSize = sizeof vi;
        GetVersionEx(&vi);
        return vi.dwMajorVersion;
}

int main(int argc,char *argv[])
{
	wcout<<L"major version: "<<GetMajorVersion()<<endl;
	 HCOUNTER hProcTime;

	        HQUERY hQuery = NULL;
        HLOG hLog = NULL;
    

		 PdhOpenQuery(NULL, 0, &hQuery);
	PdhAddCounter(hQuery,L"\\processor(1)\\% idle time" , 0, &hProcTime);				//	"\\processor(*)\\% idle time",
	while(true){
		Sleep(1000);
		time_t now_local = time(NULL);

		struct tm lt  ;

        now_local -= 3600;//prev day
		localtime_s(&lt, &now_local);
		PdhCollectQueryData(hQuery);
		PDH_FMT_COUNTERVALUE pdhValue;
		PdhGetFormattedCounterValue(hProcTime,PDH_FMT_DOUBLE,(LPDWORD)NULL, &pdhValue);

		wcout<<pdhValue.doubleValue<<endl;

		//wcout<<L"1   "<<szBuf<<endl;
		//wcout<<L"2   "<<szBuf2<<endl;
	}
    return 0;
}