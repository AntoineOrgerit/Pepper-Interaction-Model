# -*- coding: utf-8 -*-
#
# A. Orgerit
# wirst/rotate.py
# Module Pepper pour pivoter le poignet
# 07/05/2019
#

import qi
import sys
import math
import time


def main(argv):
    if len(argv) == 2:
        if argv[1] in ("LWristYaw", "RWristYaw"):
            try:
                session = qi.Session()
                try:
                    session.connect("tcp://192.168.1.7:9559")
                except RuntimeError:
                    print "{\"out\":\"error\"}"
                    sys.exit(1)

                motion_service = session.service("ALMotion")

                sensorAngles = motion_service.getAngles(argv[1], False)

                names = [argv[1]]
                angles = [math.radians(104.5), math.radians(104.5), sensorAngles[0]]
                times = [1.0, 6.0, 7.0]
                if argv[1] == "LWristYaw":
                    angles[0] = -angles[0]
                    angles[1] = -angles[1]
                motion_service.angleInterpolation(names, angles, times, False)
            except:
                print "{\"out\":\"error\"}"
        else:
            print "{\"out\":\"not valid parameter, accepted are 'LWristYaw' or 'RWristYaw'\"}"
    else:
        print "{\"out\":\"no parameter specified, accepted are 'LWristYaw' or 'RWristYaw'\"}"


if __name__ == "__main__":
    main(sys.argv)

