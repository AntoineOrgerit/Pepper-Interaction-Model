# -*- coding: utf-8 -*-
#
# A. Orgerit
# elbow/rolling.py
# Module Pepper pour pivoter le coude vers l'avant ou l'arrière
# 07/05/2019
#

import qi
import sys
import math


def main(argv):
    if len(argv) == 2:
        if argv[1] in ("LElbowRoll", "RElbowRoll"):
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
                if argv[1] == "LElbowRoll":
                    angles[0] = -angles[0]
                    angles[1] = -angles[1]
                motion_service.angleInterpolation(names, angles, times, False)
            except:
                print "{\"out\":\"error\"}"
        else:
            print "{\"out\":\"not valid parameter, accepted are 'LElbowRoll' or 'RElbowRoll'\"}"
    else:
        print "{\"out\":\"no parameter specified, accepted are 'LElbowRoll' or 'RElbowRoll'\"}"


if __name__ == "__main__":
    main(sys.argv)

