program percolate

  !
  !  Program to test for percolation of a cluster
  !

  use uni
  use percfill
  use percinit
  use percupdate
  use perctest
  use percwrite

  implicit none

  integer, parameter                 :: L = 20  ! System size
  integer, dimension(0:L+1, 0:L+1)   :: map     ! System state map

  integer :: loop, nchange
  logical :: percflag

  real    :: rho
  integer :: seed

  !
  ! Set the most important variable, the density rho
  !

  rho = 0.40

  !
  ! Set the randum number seed and initialise the generator
  !

  seed = 1564
  call rinit(seed)

  write(*,*) 'percolate: parameters are rho=', rho, ', L=', L, ', seed=', seed

  !
  ! Fill map with density rho
  !

  call perc_fill(L, map, rho)

  !
  ! Initialise map ready for updating
  !

  call perc_init(L, map)

  !
  ! Keep updating until nothing changes
  !

  loop = 1

  do

    call perc_update(L, map, nchange)

    write(*,*) 'percolate: number of changes on loop ', loop, ' is ', nchange

    if (nchange == 0) exit

    loop = loop + 1

  end do

  !
  ! Test to see if percolation occurred
  !

  call perc_test(L, map, percflag)

  if (percflag) then

    write(*,*) 'percolate: cluster DOES percolate'

  else

    write(*,*) 'percolate: cluster DOES NOT percolate'

  end if

  call perc_write(L, map, 'map.pgm', L*L)

end program percolate
