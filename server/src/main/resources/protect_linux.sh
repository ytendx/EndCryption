echo "EndCryption - general protect script | made by ytendx (Kilian P.)"
echo "Inseting IP-Table rules..."
## Blocking Invalid Packets
sudo iptables -A INPUT -f -j DROP
sudo iptables -A INPUT -p tcp --tcp-flags ALL ALL -j DROP
sudo iptables -A INPUT -p tcp --tcp-flags ALL NONE -j DROP
sudo iptables -A INPUT -m state --state INVALID -j DROP
sudo iptables -A FORWARD -m state --state INVALID -j DROP
sudo iptables -A OUTPUT -m state --state INVALID -j DROP
sudo iptables -A INPUT -m conntrack --ctstate INVALID -j DROP
sudo iptables -t mangle -A PREROUTING -p icmp -j DROP
sudo iptables -A INPUT -p tcp --tcp-flags RST RST -j DROP
sudo iptables -A INPUT -p icmp -m icmp --icmp-type address-mask-request -j DROP
sudo iptables -A INPUT -p icmp -m icmp --icmp-type timestamp-request -j DROP
sudo iptables -A INPUT -p icmp -m icmp -j DROP
sudo iptables -t raw -I PREROUTING -p tcp -m tcp --syn -j CT --notrack
sudo iptables -I INPUT -p tcp -m tcp -m conntrack --ctstate INVALID,UNTRACKED -j SYNPROXY --sack-perm --timestamp --wscale 7 --mss 14
sudo iptables -A PREROUTING -p tcp --tcp-flags FIN,SYN,RST,PSH,ACK,URG NONE -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags FIN,SYN FIN,SYN -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags SYN,RST SYN,RST -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags FIN,RST FIN,RST -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags FIN,ACK FIN -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ACK,URG URG -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ACK,FIN FIN -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ACK,PSH PSH -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ALL ALL -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ALL NONE -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ALL FIN,PSH,URG -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ALL SYN,FIN,PSH,URG -j DROP
sudo iptables -A PREROUTING -p tcp --tcp-flags ALL SYN,RST,ACK,FIN,URG -j DROP
## Block spoofed packets
sudo iptables -t mangle -A PREROUTING -s 224.0.0.0/3 -j DROP
sudo iptables -t mangle -A PREROUTING -s 169.254.0.0/16 -j DROP
sudo iptables -t mangle -A PREROUTING -s 172.16.0.0/12 -j DROP
sudo iptables -t mangle -A PREROUTING -s 192.0.2.0/24 -j DROP
sudo iptables -t mangle -A PREROUTING -s 192.168.0.0/16 -j DROP
sudo iptables -t mangle -A PREROUTING -s 10.0.0.0/8 -j DROP
sudo iptables -t mangle -A PREROUTING -s 0.0.0.0/8 -j DROP
sudo iptables -t mangle -A PREROUTING -s 240.0.0.0/5 -j DROP
sudo iptables -t mangle -A PREROUTING -s 127.0.0.0/8 ! -i lo -j DROP
## Packet Limit
sudo iptables -A INPUT -p tcp --syn -m connlimit --connlimit-above 15 --connlimit-mask 32 -j REJECT --reject-with tcp-reset
sudo iptables -A INPUT -p tcp -m connlimit --connlimit-above 75 -j REJECT --reject-with tcp-reset
## Add UDP block
sudo iptables -A INPUT -p udp -j DROP
echo "Activated Protection! - Thanks for using EndCryption :)"