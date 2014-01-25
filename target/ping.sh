#!/bin/bash
ping -w 2 -W 2 -c 2 -t 3 $1 > /dev/null && echo "true" || echo "false"