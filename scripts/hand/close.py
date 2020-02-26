# -*- coding: utf-8 -*-
#
# A. Orgerit
# hand/close.py
# Module Pepper pour l'ouverture de la main
# 24/06/2019
#

import qi
import sys


def main(argv):
    if len(argv) == 2:
        if argv[1] in ("LHand", "RHand"):
            try:
                session = qi.Session()
                try:
                    session.connect("tcp://192.168.1.7:9559")
                except RuntimeError:
                    print "{\"out\":\"error\"}"
                    sys.exit(1)

                # Get the service ALMotion.
                motion_service = session.service("ALMotion")

                # Example showing how to open the left hand
                motion_service.closeHand(argv[1])
                print "{\"out\":\"ok\"}"
            except:
                print "{\"out\":\"error\"}"
        else:
            print "{\"out\":\"not valid parameter, accepted are 'LHand' or 'RHand'\"}"
    else:
        print "{\"out\":\"no parameter specified, accepted are 'LHand' or 'RHand'\"}"


if __name__ == "__main__":
    main(sys.argv)

