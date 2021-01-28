////
//// Created by 1403 on 2020-11-26.
////
//#pragma once
//#include "wavelengthMapping.h"
//#include "../../../../sdk/native/jni/include/opencv2/core/matx.hpp"
//#include "../../../../../../Users/1403/AppData/Local/Android/Sdk/ndk/21.3.6528147/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/c++/v1/vector"
//#include "../../../../sdk/native/jni/include/opencv2/core/hal/interface.h"
//#include "../../../../sdk/native/jni/include/opencv2/core/types.hpp"
//
//using namespace std;
//using namespace cv;
//
//mappingError mapWavelength(const std::vector<cv::Vec3b>& input, double& alpha, double& beta, bool detectOverexposed = false, const vector<double>& cmf = { 445, 546, 605 });
//bool linearFitting(const vector<double>& y, vector<double>& x, double& alpha, double& beta);
//
//mappingError mapWavelength(const vector<Vec3b>& input, double& alpha, double& beta, bool detectOverexposed, const vector<double>& cmf)
//{//mappingError클래스 만들어서 mapWavelength() 만듦, return값은 4가지 경우에 따라 다름
//
//    vector<uchar> peakValue{ 0, 0, 0 }; // R, G, B 최댓값넣을 배열 초기화
//    vector<double> peakPn{ -1,-1,-1 }; // R, G, B 최댓값 pixel number넣을 배열 초기화
//
//
//    for (int pn = 0; pn < input.size(); pn++)// 최댓값 탐색  (input이 픽셀값 벡터>>일차원 벡터니까 size(), 색공간이 3c이라서 vec3b)
//    {
//        for (int c = 0; c < 3; c++)
//        {
//            Vec3b value = input[pn];
//            if (peakValue[c] < value[c])                       //한 픽셀마다 채널바꿔가며 픽셀값 탐색
//            {
//                peakValue[c] = value[c];
//                peakPn[c] = pn;
//            }
//
//            if (detectOverexposed && peakValue[c] == 255 && value[c] == 255)
//                return mappingError::OVEREXPOSED;
//        }
//    }
//
//
//    for (int c = 0; c < 3; c++)
//    {
//        if (peakPn[c] < 0)
//            return mappingError::NO_PEAK;// 최댓값 발견 못 한 경우 오류 코드 반환
//    }
//
//    // 스펙트럼 판단
//    // if (!((peakPn[B] < peakPn[G]) && (peakPn[G] < peakPn[R]) && (peakPn[B] < peakPn[R])))
//    //   return mappingError::NOT_LINEAR;
//
//    // Liear Fitting
//    linearFitting(cmf, peakPn, alpha, beta);
//    return mappingError::OK;
//}
//
//bool linearFitting(const vector<double>& y, vector<double>& x, double& alpha, double& beta)
//{
//    // x, y 데이터 개수가 다르면 false 반환
//    int n = static_cast<int>(y.size());
//    if (n != x.size())
//        return false;
//
//    // Linear fitting
//    double xsum = 0, x2sum = 0, ysum = 0, xysum = 0;
//    for (int i = 0; i < n; i++)
//    {
//        xsum += x[i];
//        ysum += y[i];
//        x2sum += pow(x[i], 2); // x[i] ^ 2
//        xysum += x[i] * y[i];
//    }
//    alpha = (n * xysum - xsum * ysum) / (n * x2sum - xsum * xsum); // 기울기
//    beta = (x2sum*ysum - xsum * xysum) / (x2sum * n - xsum * xsum); // y절편
//    return true;
//}
//
//
//double distance(const Point& p1, const Point& p2)
//{
//    return sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2));
//}
