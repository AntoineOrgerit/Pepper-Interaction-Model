# -*- coding: utf-8 -*-
#
# A. Orgerit
# shoulder/pitching.py
# Module Pepper pour pivoter l'épaule vers l'avant ou l'arrière
# 07/05/2019
#

import qi
import sys
import math


def main(argv):
    if len(argv) == 2:
        if argv[1] in ("LShoulderPitch", "RShoulderPitch"):
            try:
                session = qi.Session()
                try:
                    session.connect("tcp://192.168.1.7:9559")
                except RuntimeError:
                    print "{\"out\":\"error\"}"
                    sys.exit(1)

                motion_service = session.service("ALMotion")

                sensorAngles = motion_service.getAngles(argv[1], False)

                print sensorAngles

                names = [argv[1]]
                angles = [math.radians(-30), math.radians(-30), sensorAngles[0]]
                times = [1.0, 6.0, 7.0]
                # if argv[1] == "LShoulderPitch":
                #     angles[0] = -angles[0]
                #     angles[1] = -angles[1]
                print angles
                motion_service.angleInterpolation(names, angles, times, False)
            except:
                print "{\"out\":\"error\"}"
        else:
            print "{\"out\":\"not valid parameter, accepted are 'LShoulderPitch' or 'RShoulderPitch'\"}"
    else:
        print "{\"out\":\"no parameter specified, accepted are 'LShoulderPitch' or 'RShoulderPitch'\"}"


if __name__ == "__main__":
    main(sys.argv)