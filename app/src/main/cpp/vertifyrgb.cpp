////
//// Created by 1403 on 2020-11-26.
////
//#pragma once
//#include "vertifyrgb.h"
//#include "../../../../sdk/native/jni/include/opencv2/core/types.hpp"
//
//using namespace cv;
//using namespace std;
//
//bool vertify_rgb(int& p1, int& p2, int& p3, int& p4)
//{
//    // 스펙트럼 내 rgb객체 각각 검출 조건 , 그냥 각 채널에서 제일 큰거 고르면 되긴할 듯
//
//    float size = p3*p4;
//    float aspect = (float)p4/p3;
//    if(aspect<1) aspect =1/ aspect;
//
//    //카메라 변경시 조건 확인
//    bool ch1= size>7000 && size<60000;// 최대 사이즈 수정확인 하기
//
//    bool ch2 = aspect >1.0&& aspect<3.0;
//
//    return ch1&&ch2;
//}
//
