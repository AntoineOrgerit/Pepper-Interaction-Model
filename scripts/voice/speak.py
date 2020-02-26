# -*- coding: utf-8 -*-
#
# A. Orgerit
# voice/speak.py
# Module Pepper pour parler grâce à un texte en entrée
# 07/05/2019
#

import qi
import sys


def main(argv):
    if len(argv) == 2:
        try:
            session = qi.Session()
            try:
                session.connect("tcp://192.168.1.7:9559")
            except RuntimeError:
                print "{\"out\":\"error\"}"
                sys.exit(1)

            tts = session.service("ALTextToSpeech")

            tts.say(argv[1])
            print "{\"out\":\"ok\"}"
        except:
            print "{\"out\":\"error\"}"
    else:
        print "{\"out\":\"no parameter specified, expected text to speak\"}"


if __name__ == "__main__":
    main(sys.argv)

