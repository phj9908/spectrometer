//
// Created by 1403 on 2020-11-26.
//
//#include <jni.h>
#include "opencv2\opencv.hpp"
#include <iostream>
#include <opencv2/imgproc.hpp>
#include <String>
#include <algorithm>
#include <vector>


#ifndef VERTIFYROI
#define VERTIFYROI


using namespace cv;
using namespace std;

bool vertify_roi(cv::RotatedRect mr);  // 스펙트럼의 주변영역 검출 조건, roi로 사용


void find_rect(Mat img, vector<RotatedRect>& candidates);//입력이미지로부터 직사각형 부분 찾기


void return_roi(Mat &img, Mat &dst, RotatedRect mr,Rect &roi_rect); //(Mat에 저장한 img, 직사각형 벡터집합 ,선 색, 선 굵기)

#endif //MY_APPLICATION1125_1_VERTIFY_ROI_H

