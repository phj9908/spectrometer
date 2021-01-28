//
// Created by 1403 on 2020-11-26.
//
#include "opencv2\opencv.hpp"
#include <iostream>
#include <opencv2/imgproc.hpp>
#include <String>
#include <algorithm>
#include <vector>
#ifndef WAVELENGTH_MAPPING
#define WAVELENGTH_MAPPING

#define BGR_B 0
#define BGR_G 1
#define BGR_R 2

#include "opencv2/opencv.hpp"

enum mappingError {
    OK, // ���� ����
    NO_PEAK, // BGR_R, BGR_G, BGR_B peak�� �߰� ����
    NOT_LINEAR, // ���������� ���� --> ����Ʈ���� �ƴ�
    OVEREXPOSED, // 255(8bit �ִ�) ���� ������ �ȼ��� ������ ���� --> ������ �߻�
};

mappingError mapWavelength(const std::vector<cv::Vec3b>& input, double& alpha, double& beta,
                           bool detectOverexposed = false, const std::vector<double>& cmf = { 445, 545, 605 });

mappingError mapWavelength(const std::vector<uchar>& r, const std::vector<uchar>& g, const std::vector<uchar>& b,
                           double& alpha, double& beta,
                           bool detectOverexposed = false, const std::vector<double>& cmf = { 445, 545, 605 });


bool linearFitting(const std::vector<double>& y, std::vector<double>& x, double& alpha, double& beta);


//void setGraph(cv::Mat& graph, const cv::Range& xRange, const cv::Range& yRange, int xScaleBreak = 50, int yScaleBreak = 50);
#endif//MY_APPLICATION1125_1_WAVELENGTHMAPPING_H
