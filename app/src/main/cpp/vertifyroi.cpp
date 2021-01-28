////
//// Created by 1403 on 2020-11-26.
////
//#pragma once
//#include "vertifyroi.h"
//#include "../../../../sdk/native/jni/include/opencv2/core/types.hpp"
//#include "../../../../sdk/native/jni/include/opencv2/core/matx.hpp"
//#include "../../../../sdk/native/jni/include/opencv2/core/mat.hpp"
//#include "../../../../sdk/native/jni/include/opencv2/core.hpp"
//
//
//using namespace std;
//using namespace cv;
//
//bool vertify_roi(RotatedRect mr)  // 스펙트럼의 주변영역 검출 조건, roi로 사용
//{
//    float size = mr.size.area();
//    float aspect = (float)mr.size.height / mr.size.width;        //종횡비 계산
//    if (aspect < 1) aspect = 1 / aspect;                         //종횡비 1보다 작으면 역수로(종횡비가 1보다 작으면 세로로 긴 영역임)
//
//    bool ch1 = size < 270000&& size >100000;                      //직사각형 넓이 조건
//    bool ch2 = aspect > 2.5 && aspect <6.0;                     //종횡비 조건
//
////    bool ch1 = size > 2;                    //직사각형 넓이 조건
////    bool ch2 = aspect > 1.0;
//
//    return ch1&&ch2;                                            //조건 반환
//}
//
//
//void find_rect(Mat img, vector<RotatedRect>& candidates) //입력이미지로부터 직사각형 부분 찾기
//{
//    cvtColor(img,img,COLOR_BGR2GRAY);
//    threshold(img,img,10,255,THRESH_BINARY);
//
//    int morph_size = 5;
//    Mat element = getStructuringElement(MORPH_RECT, Size(2 * morph_size + 1, 2 * morph_size + 1), Point(-1, -1));
//    morphologyEx(img,img,MORPH_OPEN,element);
//
//    vector< vector<Point>> contours;                                                //외곽선 벡터 선언
//    findContours(img.clone(), contours, RETR_TREE, CHAIN_APPROX_SIMPLE);            //입력영상의 외곽선을 벡터 'contours'로 반환
//    //윤곽검색모드,윤곽근사메소드값
//
//    for (int i = 0; i < (int)contours.size(); i++)                                  //벡터 배열의 객체 갯수=for문 횟수, 검출외곽선 조회
//    {
//        RotatedRect rot_rect = minAreaRect(contours[i]);                            //RotatedRect 객체 rot_rect 선언
//        //minAreaRect():검출된 외곽선 벡터들의 최소영역을 회전사각형'rot_rect'으로 반환
//
//        if (vertify_roi(rot_rect))                                                 //직사각형 조건에 해당하면
//            candidates.push_back(rot_rect);                                          //직사각형모양 벡터 rot_rect를 candidate라는 벡터로 저장해둠
//    }
//}
//
//void return_roi(Mat &img, Mat &dst, RotatedRect mr, Scalar color, int thickness) //(Mat에 저장한 img, 직사각형 벡터집합 ,선 색, 선 굵기)
//{                                                                           //검출된 직사각형 벡터를 영상에 그림
//
//    Point2f pts[4];                                         //점 배열객체 4개 선언
//    mr.points(pts);                                         //검출된 직사각형 벡터를 points()을 이용하여 좌표(사각형 꼭짓점)로 변환후 pts배열에 반환
//
//    Mat roi(img, Rect(pts[2].x, pts[2].y, mr.size.height, mr.size.width));         //img에다가  mr직사각형모양 관심영역 생성,그 관심영역을 'roi'라는 이름으로 Mat 객체 생성
//
//    for (int i = 0; i < 4; ++i)
//    {
//        line(img, pts[i], pts[(i + 1) % 4], color, thickness);      //line(img,점1,점1과 연결할 점2,색, 굵기)
//    }
//    //i가 0~3까지 니까 i=3일 때, pts[3]과 pts[0]을 이어야 하기에 3chanel에 (i+1)%4로 해야함
//
//    dst = roi; // dst 리턴하기
//}
