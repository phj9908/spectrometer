////
//// Created by 1403 on 2020-12-07.
////
//
//#include "findP0_getPixelNumber.h"
//#pragma once
//
//
//using namespace cv;
//using namespace std;
//
//
//uchar getMax(Vec3b& value)
//{
//    uchar max = 0;
//    int maxi = 0;
//    for (int i = 0; i < 3; i++)
//    {
//        if (max < value[i])
//        {
//            max = value[i];
//            maxi = i;
//        }
//    }
//    return max;
//}
//
////bool compressY(Mat& img, int& top, int& height) // img: 1-channel mat
////{
////    // 이진화
////    threshold(img, img, 0, 0xFF, THRESH_BINARY | THRESH_OTSU);
////
////    // 모폴로지 (잡음 제거)
////    morphologyEx(img, img, MORPH_OPEN, getStructuringElement(MORPH_RECT, Size(5, 5)));
////
////    // 레이블링
////    Mat labels, stats, centroids;
////    int numLabels = connectedComponentsWithStats(img, labels, stats, centroids);
////    if (numLabels < 2)
////        return false;
////
////    // 가장 area가 큰 레이블을 찾음
////    int largestArea = 0;
////    int largestIdx = 0;
////
////    for (int i = 1; i < numLabels; i++)
////    {
////        int area = stats.at<int>(i, CC_STAT_AREA);
////        if (area > largestArea)
////        {
////            largestArea = area;
////            largestIdx = i;
////        }
////    }
////
////    // 해당 레이블의 y, height를 return
////    top = stats.at<int>(largestIdx, CC_STAT_TOP);
////    height = stats.at<int>(largestIdx, CC_STAT_HEIGHT);
////
////    return true;
////}
//
//
//int findP0(const Mat& img) // idea) p0도 여러 frame에 걸쳐 moving averaging 등 평균화 해주면 좋을 듯함
//{
//    /*
//    B-channel에서 가장 밝은(=평균 B 값이 큰) 열(column)을 선정한다.
//    p0 <= 선정된 열의 픽셀 넘버
//    p0의 wavelength = 450 nm // 매핑 기준점이라는 게 시작점이란 건가?
//    p0가 픽셀 넘버와 스펙트럼 간 매핑 기준점이 된다. (마치 바코드의 기준점 처럼)
//    */
//    int max = 0;
//    int width = img.cols;
//    int height = img.rows;
//    int p0 = 0;
//
//    for (int x = 0; x < width; x++)
//    {
//        LineIterator iter(img, Point(x, 0), Point(x, height - 1));
//        int sum = 0;
//        for (int y = 0; y < iter.count; y++, ++iter)
//        {
//            sum += *(const uchar*)*iter;
//        }
//        if (sum > max)
//        {
//            max = sum;
//            p0 = x;
//        }
//    }
//    return p0;
//}
//
//void getMeasurePixelNumber(const vector<double>& wavelength, vector<double>& pixelNumber, int p0, double GRADIENT)
//{
//    int WVL_ZERO=450;
//
//    int numMeasure = wavelength.size();
//    for (int i = 0; i < numMeasure; i++)
//    {
//        pixelNumber[i] = (wavelength[i] - WVL_ZERO)*GRADIENT + p0;
//    }
//}
//
